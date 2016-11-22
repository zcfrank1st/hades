package com.chaos.hades.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcfrank1st on 21/11/2016.
 */
public class Hades { // dev profile default
    private static final String ENV_DEV = "dev";
    private static final String ENV_PROD = "prod";
    private static final String CODEC = "UTF-8";

    private CuratorFramework curator;
    private HadesProfile profile = HadesProfile.DEV;

    private Hades (String connections) {
        this.curator = CuratorFrameworkFactory.builder()
                .connectString(connections)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        this.curator.start();
    }

    public static class HadesBuilder {
        private String connections;

        public HadesBuilder connections(String connections) {
            this.connections = connections;
            return this;
        }

        public Hades build () {
            return new Hades(connections);
        }
    }

    public Hades profile (HadesProfile profile) {
        this.profile = profile;
        return this;
    }

    public void initProject (String project) throws Exception {
        curator.create().creatingParentsIfNeeded().forPath("/" + ENV_DEV + "/" + project);
        curator.create().creatingParentsIfNeeded().forPath("/" + ENV_PROD + "/" + project);
    }

    public void deleteProject (String project) throws Exception {
        curator.delete().deletingChildrenIfNeeded().forPath("/"+ ENV_DEV + "/" + project);
        curator.delete().deletingChildrenIfNeeded().forPath("/" + ENV_PROD + "/" + project);
    }

    public void addConf (String project, String key, String value) throws Exception {
        switch (profile) {
            case PRD:
                curator.create().forPath("/" + ENV_PROD +"/" + project + "/" + key, value.getBytes(CODEC));
                break;
            case DEV:
            default:
                curator.create().forPath("/" + ENV_DEV + "/" + project + "/" + key, value.getBytes(CODEC));
                break;
        }
    }

    public void deleteConf(String project, String key) throws Exception {
        switch (profile) {
            case PRD:
                curator.delete().forPath("/" + ENV_PROD + "/" + project + "/" + key);
                break;
            case DEV:
            default:
                curator.delete().forPath("/" + ENV_DEV + "/" + project + "/" + key);
                break;
        }
    }

    public void updateConf(String project, String key, String value) throws Exception {
        switch (profile) {
            case PRD:
                curator.setData().forPath("/" + ENV_PROD + "/" + project + "/" + key, value.getBytes(CODEC));
                break;
            case DEV:
            default:
                curator.setData().forPath("/" + ENV_DEV + "/" + project + "/" + key, value.getBytes(CODEC));
                break;
        }
    }

    public String getConf (String project, String key) throws Exception {
        byte[] data;
        switch (profile) {
            case PRD:
                data = curator.getData().forPath("/" + ENV_PROD + "/" + project + "/" + key);
                break;
            case DEV:
            default:
                data = curator.getData().forPath("/" + ENV_DEV + "/" + project + "/" + key);
                break;
        }
        return null == data ? null : new String(data, CODEC);
    }

    public Map<String, String> scanProjectConf(String project) throws Exception {
        Map<String, String> projectConfs = new HashMap<>();
        switch (profile) {
            case PRD:
                String prodPath = "/" + ENV_PROD + "/" + project;
                List<String> projectPrdConfKeys = curator.getChildren().forPath(prodPath);

                for (String key: projectPrdConfKeys) {
                    byte[] data = curator.getData().forPath(prodPath + "/" + key);
                    projectConfs.put(key, data == null ? null : new String(data, CODEC));
                }

                break;
            case DEV:
            default:
                String devPath = "/" + ENV_DEV + "/" + project;
                List<String> projectDevConfKeys = curator.getChildren().forPath(devPath);

                for (String key: projectDevConfKeys) {
                    byte[] data = curator.getData().forPath(devPath + "/" + key);
                    projectConfs.put(key, data == null ? null : new String(data, CODEC));
                }
                break;
        }

        return projectConfs;
    }

    public void destroy() {
        this.curator.close();
    }
}
