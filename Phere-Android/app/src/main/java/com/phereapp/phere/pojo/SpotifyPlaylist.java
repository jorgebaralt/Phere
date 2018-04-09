package com.phereapp.phere.pojo;

import com.google.gson.annotations.SerializedName;

public class SpotifyPlaylist {
    private String name;
    @SerializedName("collaborative")
    private boolean isCollaborative;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("id")
    private String playlistId;

}
