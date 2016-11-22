package com.chaos.hades.web.model;

import lombok.Data;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
@Data
public class ConfigContent {
    private String project;
    private Integer env;
    private String key;
    private String value;
}
