package com.phereapp.phere.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpotifyPlaylist implements Serializable {
    private String name;
    @SerializedName("collaborative")
    private boolean isCollaborative;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("id")
    private String playlistId;

    public String getName() {
        return name;
    }

    public boolean isCollaborative() {
        return isCollaborative;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getPlaylistId() {
        return playlistId;
    }
}
