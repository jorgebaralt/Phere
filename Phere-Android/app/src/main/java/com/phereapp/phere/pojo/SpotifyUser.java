package com.phereapp.phere.pojo;

import com.google.gson.annotations.SerializedName;

public class SpotifyUser {
    private String birthdate;
    @SerializedName("display_name")
    private String displayName;
    private String id;
    private String uri;
    private String href;

    public SpotifyUser(){

    }

    public String getId() {
        return id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUri() {
        return uri;
    }

    public String getHref() {
        return href;
    }
}
