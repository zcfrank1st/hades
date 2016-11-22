package com.chaos.hades.web.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
@Component
public class ConfigLoader {
    public Config config = ConfigFactory.load();
}
