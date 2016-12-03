package com.chaos.hades.client;

import com.chaos.hades.core.Hades;
import com.chaos.hades.core.HadesProfile;
import org.apache.curator.framework.recipes.cache.TreeCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zcfrank1st on 03/12/2016.
 */
public class HotSwapHadesClient implements HadesClient {
    private Map<String, String> configs = new HashMap<>();

    public HotSwapHadesClient(String connectionStrings, HadesProfile profile, String project) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(connectionStrings).build();
        this.configs = hades.profile(profile).scanProjectConf(project);

        String profileString;
        if (profile == HadesProfile.PRD) {
            profileString = "prod";
        } else {
            profileString = "dev";
        }

        new Thread(() -> {
            TreeCache tc = new TreeCache(hades.getCurator() , "/" + profileString + "/" + project);

            ExecutorService es = Executors.newCachedThreadPool();
            tc.getListenable().addListener((client, event) -> {
                switch (event.getType()) {
                    case NODE_ADDED:
                        String[] addKeySegments = event.getData().getPath().split("/");
                        String addKey = addKeySegments[addKeySegments.length - 1];

                        configs.put(addKey, new String(event.getData().getData(), "UTF-8"));
                        break;
                    case NODE_REMOVED:
                        String[] removeKeySegments = event.getData().getPath().split("/");
                        String removeKey = removeKeySegments[removeKeySegments.length - 1];

                        configs.remove(removeKey);
                        break;
                    default:
                        break;
                }
            }, es);

            try {
                tc.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String getOrElse (String key, String other) {
        String res = configs.get(key);
        return res == null ? other : res;
    }
}
