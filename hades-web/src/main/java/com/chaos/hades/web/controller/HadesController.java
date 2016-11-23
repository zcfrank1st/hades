package com.chaos.hades.web.controller;

import com.chaos.hades.web.model.ConfigContent;
import com.chaos.hades.web.model.GitlabRole;
import com.chaos.hades.web.model.Message;
import com.chaos.hades.web.service.AuthorizationService;
import com.chaos.hades.web.service.HadesService;
import com.chaos.hades.web.util.GitlabAccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
@RestController
public class HadesController {
    private final HadesService hadesService;
    private final AuthorizationService authorizationService;

    @Autowired
    public HadesController(HadesService hadesService, AuthorizationService authorizationService) {
        this.hadesService = hadesService;
        this.authorizationService = authorizationService;
    }

    @RequestMapping(value = "/config/{project}", method = RequestMethod.GET)
    public ResponseEntity existsProject(
            @RequestHeader("token") String token,
            @RequestHeader("projectpath") String projectPath,
            @RequestHeader("username") String userName,
            @PathVariable("project") String project) throws Exception {

        GitlabRole role = checkRole(token, projectPath, userName);
        Message message = new Message();
        switch (role) {
            case MASTER:
                message.setCode(0);
                message.setBody(hadesService.existsProject(project));
                message.setMessage("ok");
                break;
            case CLIENT:
                message.setCode(1);
                message.setBody(hadesService.existsProject(project));
                message.setMessage("no permission");
                break;
        }

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/config/skeleton", method = RequestMethod.POST)
    public ResponseEntity initProjectSkeleton(
            @RequestHeader("token") String token,
            @RequestHeader("projectpath") String projectPath,
            @RequestHeader("username") String userName,
            @RequestBody String project) throws Exception {

        GitlabRole role = checkRole(token, projectPath, userName);
        Message message = new Message();
        switch (role) {
            case MASTER:
                hadesService.initProjectSkeleton(project);

                message.setCode(0);
                message.setBody(null);
                message.setMessage("ok");
                break;
            case CLIENT:
                message.setCode(1);
                message.setBody(null);
                message.setMessage("no permission");
                break;
        }

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/config/skeleton/{project}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectSkeleton(
            @RequestHeader("token") String token,
            @RequestHeader("projectpath") String projectPath,
            @RequestHeader("username") String userName,
            @PathVariable("project") String project) throws Exception {

        GitlabRole role = checkRole(token, projectPath, userName);
        Message message = new Message();
        switch (role) {
            case MASTER:
                hadesService.deleteProjectSkeleton(project);

                message.setCode(0);
                message.setBody(null);
                message.setMessage("ok");
                break;
            case CLIENT:
                message.setCode(1);
                message.setBody(null);
                message.setMessage("no permission");
                break;
        }

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public ResponseEntity addConfig(
            @RequestHeader("token") String token,
            @RequestHeader("projectpath") String projectPath,
            @RequestHeader("username") String userName,
            @RequestBody ConfigContent configContent) throws Exception {

        GitlabRole role = checkRole(token, projectPath, userName);
        Message message = new Message();
        switch (role) {
            case MASTER:
                hadesService.addConfig(configContent);
                message.setCode(0);
                message.setBody(null);
                message.setMessage("ok");
                break;
            case CLIENT:
                message.setCode(1);
                message.setBody(null);
                message.setMessage("no permission");
                break;
        }

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/config/{project}/{env}/{key}", method = RequestMethod.DELETE)
    public ResponseEntity deleteConfig(
            @RequestHeader("token") String token,
            @RequestHeader("projectpath") String projectPath,
            @RequestHeader("username") String userName,

            @PathVariable("project") String project,
            @PathVariable("env") Integer env,
            @PathVariable("key") String key) throws Exception {

        GitlabRole role = checkRole(token, projectPath, userName);
        Message message = new Message();
        switch (role) {
            case MASTER:
                hadesService.deleteConfig(project, env, key);
                message.setCode(0);
                message.setBody(null);
                message.setMessage("ok");
                break;
            case CLIENT:
                message.setCode(1);
                message.setBody(null);
                message.setMessage("no permission");
                break;
        }

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResponseEntity scanConfigs(
            @RequestHeader("token") String token,
            @RequestHeader("projectpath") String projectPath,
            @RequestHeader("username") String userName,
            @RequestParam("project") String project, @RequestParam("env") Integer env) throws Exception {

        GitlabRole role = checkRole(token, projectPath, userName);
        Message message = new Message();
        switch (role) {
            case MASTER:
                message.setCode(0);
                message.setBody(hadesService.scanConfigs(project, env));
                message.setMessage("ok");
                break;
            case CLIENT:
                message.setCode(0);
                message.setBody(authorizationService.maskValue(hadesService.scanConfigs(project, env)));
                message.setMessage("ok");
                break;
        }

        return ResponseEntity.ok(message);
    }

    private GitlabRole checkRole (String token,
                                  String projectPath,
                                  String userName) throws IOException {

        int accessCode = authorizationService.getGitlabProjectAccessLevel(token, projectPath, userName);

        if (GitlabAccessCode.MIN_CAN_MODIFY <= accessCode) {
            return GitlabRole.MASTER;
        } else {
            return GitlabRole.CLIENT;
        }

    }
}
