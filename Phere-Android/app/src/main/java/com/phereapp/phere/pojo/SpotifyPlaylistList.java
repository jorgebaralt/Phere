package com.phereapp.phere.pojo;

import com.google.gson.annotations.SerializedName;

public class SpotifyPlaylistList {
    @SerializedName("items")
    private SpotifyPlaylist[] spotifyPlaylists;
}
