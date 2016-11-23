package com.chaos.hades.web.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by zcfrank1st on 23/11/2016.
 */
@Data
public class GitlabProjectMember {
    private String name;
    private String username;
    private Integer id;
    private String state;

    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("web_url")
    private String webUrl;
    @SerializedName("access_level")
    private Integer accessLevel;
    @SerializedName("expires_at")
    private String expiresAt;
}
