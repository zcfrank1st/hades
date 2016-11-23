package com.chaos.hades.web.service;

import com.chaos.hades.web.model.GitlabProjectMember;
import com.chaos.hades.web.util.ConfigLoader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
@Service
public class AuthorizationService {
    private final ConfigLoader configLoader;
    private final Gson gson;

    @Autowired
    public AuthorizationService(ConfigLoader configLoader, Gson gson) {
        this.configLoader = configLoader;
        this.gson = gson;
    }

    public int getGitlabProjectAccessLevel(String privateToken, String projectPath, String username) throws IOException {
        String result = Request
                .Get(configLoader.config.getString("gitlab.project.api") + projectPath.replace("/", "%2F") + "/members")
                .addHeader("PRIVATE-TOKEN", privateToken)
                .execute()
                .returnContent()
                .toString();

        List<GitlabProjectMember> memberList = gson.fromJson(result, new TypeToken<List<GitlabProjectMember>>(){}.getType());

        for(GitlabProjectMember member : memberList) {
            if (member.getUsername().equals(username)) {
                return member.getAccessLevel();
            }
        }

        return 0;
    }

    public String maskValue(String value) {
        return valueWithSalts(value.length());
    }

    private String valueWithSalts (int length) {
        String[] salts = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
                "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
                "a", "s", "d", "f", "g", "h", "j", "k", "l",
                "z", "x", "c", "v", "b", "n", "m",
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Z", "X", "C", "V", "B", "N", "M"
        };

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= length -1; i++) {
            Random random = new Random();
            int current = random.nextInt(62);
            sb.append(salts[current]);
        }

        return sb.toString();
    }
}
