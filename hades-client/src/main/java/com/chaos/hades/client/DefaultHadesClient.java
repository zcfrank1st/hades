package com.chaos.hades.client;

import com.chaos.hades.core.Hades;
import com.chaos.hades.core.HadesProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class DefaultHadesClient implements HadesClient {
    private Map<String, String> configs = new HashMap<>();

    public DefaultHadesClient(String connectionStrings, HadesProfile profile, String project) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(connectionStrings).build();
        this.configs = hades.profile(profile).scanProjectConf(project);
        hades.destroy();
    }

    public String getOrElse (String key, String other) {
        String res = configs.get(key);
        return res == null ? other : res;
    }
}
