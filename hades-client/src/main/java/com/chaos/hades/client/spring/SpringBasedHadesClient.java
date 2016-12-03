package com.chaos.hades.client.spring;

import com.chaos.hades.client.DefaultHadesClient;
import com.chaos.hades.client.HadesClient;
import com.chaos.hades.client.HotSwapHadesClient;
import com.chaos.hades.core.HadesProfile;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class SpringBasedHadesClient {
    private HadesClient client;

    // -Dhades.env=prod
    public SpringBasedHadesClient (String app, String connections, String type) throws Exception {
        String env = System.getProperty("hades.env");

        if (null != env && env.equals("prod")) {
            if ("common".equals(type)) {
                this.client = new DefaultHadesClient(connections, HadesProfile.PRD, app);
            } else if ("hotswap".equals("type")) {
                this.client = new HotSwapHadesClient(connections, HadesProfile.PRD, app);
            } else {
                throw new RuntimeException("no such hades client type!");
            }
        } else {
            if ("common".equals(type)) {
                this.client = new DefaultHadesClient(connections, HadesProfile.DEV, app);
            } else if ("hotswap".equals("type")) {
                this.client = new HotSwapHadesClient(connections, HadesProfile.DEV, app);
            } else {
                throw new RuntimeException("no such hades client type!");
            }
        }
    }

    public String getOrElse (String key, String other) {
        return client.getOrElse(key, other);
    }
}
