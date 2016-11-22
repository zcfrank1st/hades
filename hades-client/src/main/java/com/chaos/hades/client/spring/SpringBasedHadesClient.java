package com.chaos.hades.client.spring;

import com.chaos.hades.client.DefaultHadesClient;
import com.chaos.hades.core.HadesProfile;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class SpringBasedHadesClient {
    private DefaultHadesClient client;

    // -Dhades.env=prod
    public SpringBasedHadesClient (String app, String connections) throws Exception {
        String env = System.getProperty("hades.env");

        if (null != env && env.equals("prod")) {
            this.client = new DefaultHadesClient(connections, HadesProfile.PRD, app);
        } else {
            this.client = new DefaultHadesClient(connections, HadesProfile.DEV, app);
        }
    }

    public String getOrElse (String key, String other) {
        return client.getOrElse(key, other);
    }
}
