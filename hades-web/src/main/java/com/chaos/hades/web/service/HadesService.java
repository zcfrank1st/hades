package com.chaos.hades.web.service;

import com.chaos.hades.core.Hades;
import com.chaos.hades.core.HadesProfile;
import com.chaos.hades.web.model.ConfigContent;
import com.chaos.hades.web.util.ConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
@Service
public class HadesService {
    private static final String CONNECTION_STINGS = "hades.connections";
    private final ConfigLoader loader;

    @Autowired
    public HadesService(ConfigLoader loader) {
        this.loader = loader;
    }

    public boolean existsProject(String project) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(loader.config.getString(CONNECTION_STINGS)).build();
        boolean res = hades.existProject(project);
        hades.destroy();

        return res;
    }

    public void initProjectSkeleton(String project) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(loader.config.getString(CONNECTION_STINGS)).build();
        hades.initProject(project);
        hades.destroy();
    }

    public void deleteProjectSkeleton(String project) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(loader.config.getString(CONNECTION_STINGS)).build();
        hades.deleteProject(project);
        hades.destroy();
    }

    public void addConfig(ConfigContent configContent) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(loader.config.getString(CONNECTION_STINGS)).build();
        switch (configContent.getEnv()) {
            case 0:
                hades.profile(HadesProfile.PRD).addConf(configContent.getProject(), configContent.getKey(), configContent.getValue());
                break;
            default:
                hades.profile(HadesProfile.DEV).addConf(configContent.getProject(), configContent.getKey(), configContent.getValue());
                break;
        }
        hades.destroy();
    }

    public void deleteConfig(String project, Integer env, String key) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(loader.config.getString(CONNECTION_STINGS)).build();
        switch (env) {
            case 0:
                hades.profile(HadesProfile.PRD).deleteConf(project, key);
                break;
            default:
                hades.profile(HadesProfile.DEV).deleteConf(project, key);
                break;
        }
        hades.destroy();
    }

    public Map<String, String> scanConfigs(String project, Integer env) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections(loader.config.getString(CONNECTION_STINGS)).build();
        Map<String, String> configs;
        switch (env) {
            case 0:
                configs = hades.profile(HadesProfile.PRD).scanProjectConf(project);
                break;
            default:
                configs = hades.profile(HadesProfile.DEV).scanProjectConf(project);
                break;
        }
        hades.destroy();
        return configs;
    }
}
