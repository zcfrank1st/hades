package com.chaos.hades.web.model;

import lombok.Data;

/**
 * Created by zcfrank1st on 23/11/2016.
 */
@Data
public class Message<T> {
    private int code;
    private String message;
    private T body;
}
