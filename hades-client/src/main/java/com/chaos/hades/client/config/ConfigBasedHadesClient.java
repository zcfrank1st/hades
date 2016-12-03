package com.chaos.hades.client.config;

import com.chaos.hades.client.DefaultHadesClient;
import com.chaos.hades.client.HadesClient;
import com.chaos.hades.client.HadesClientType;
import com.chaos.hades.client.HotSwapHadesClient;
import com.chaos.hades.core.HadesProfile;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class ConfigBasedHadesClient {
    private HadesClient client;
    private HadesClientType type = HadesClientType.COMMON;

    // -Dhades.env=prod
    public ConfigBasedHadesClient () throws Exception {
        Config conf = ConfigFactory.load();
        String env = System.getProperty("hades.env");

        if (null != env && env.equals("prod")) {
            if (type == HadesClientType.COMMON)
                this.client = new DefaultHadesClient(conf.getString("hades.connections"), HadesProfile.PRD, conf.getString("hades.app"));
            else if (type == HadesClientType.HOWSWAP) {
                this.client = new HotSwapHadesClient(conf.getString("hades.connections"), HadesProfile.PRD, conf.getString("hades.app"));
            }
        } else {
            if (type == HadesClientType.COMMON)
                this.client = new DefaultHadesClient(conf.getString("hades.connections"), HadesProfile.DEV, conf.getString("hades.app"));
            else if (type == HadesClientType.HOWSWAP) {
                this.client = new HotSwapHadesClient(conf.getString("hades.connections"), HadesProfile.DEV, conf.getString("hades.app"));
            }
        }
    }

    public void setClientType (HadesClientType type) {
        this.type = type;
    }

    public String getOrElse (String key, String other) {
        return client.getOrElse(key, other);
    }
}
