package com.chaos.hades.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by zcfrank1st on 21/11/2016.
 */
public class Hades { // dev profile default
    private static final String ENV_DEV = "dev";
    private static final String ENV_PROD = "prod";
    private static final String CODEC = "UTF-8";

    private CuratorFramework curator;
    private HadesProfile profile;

    private Hades (String connections) {
        this.curator = CuratorFrameworkFactory.builder()
                .connectString(connections)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
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
        try {
            curator.start();
            curator.create().forPath("/" + ENV_DEV + "/" + project);
            curator.create().forPath("/" + ENV_PROD + "/" + project);
        } finally {
            curator.close();
        }
    }

    public void deleteProject (String project) throws Exception {
        try {
            curator.start();
            curator.delete().forPath("/"+ ENV_DEV + "/" + project);
            curator.delete().forPath("/" + ENV_PROD + "/" + project);
        } finally {
            curator.close();
        }
    }

    public void addConf (String project, String key, String value) throws Exception {
        try {
            curator.start();
            switch (profile) {
                case PRD:
                    curator.create().forPath("/" + ENV_PROD +"/" + project + "/" + key, value.getBytes(CODEC));
                    break;
                case DEV:
                default:
                    curator.create().forPath("/" + ENV_DEV + "/" + project + "/" + key, value.getBytes(CODEC));
                    break;
            }
        } finally {
            curator.close();
        }
    }

    public void deleteConf(String project, String key) throws Exception {
        try {
            curator.start();

            switch (profile) {
                case PRD:
                    curator.delete().forPath("/" + ENV_PROD + "/" + project + "/" + key);
                    break;
                case DEV:
                default:
                    curator.delete().forPath("/" + ENV_DEV + "/" + project + "/" + key);
                    break;
            }
        } finally {
            curator.close();
        }
    }

    public void updateConf(String project, String key, String value) throws Exception {
        try {
            curator.start();
            switch (profile) {
                case PRD:
                    curator.setData().forPath("/" + ENV_PROD + "/" + project + "/" + key, value.getBytes(CODEC));
                    break;
                case DEV:
                default:
                    curator.setData().forPath("/" + ENV_DEV + "/" + project + "/" + key, value.getBytes(CODEC));
                    break;
            }
        } finally {
            curator.close();
        }
    }

    public String getConfOrElse (String project, String key, String other) throws Exception {
        try {
            curator.start();
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
            return null == data ? other : new String(data, CODEC);
        } finally {
            curator.close();
        }
    }
}
