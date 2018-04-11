package com.phereapp.phere.pojo;

import com.google.gson.annotations.SerializedName;

public class SpotifyPlaylistOwner {
    @SerializedName("display_name")
    private String displayName;
    private String id;
    private String href;

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }
}
