package com.phereapp.phere.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyPlaylistList{
    @SerializedName("items")
    private List<SpotifyPlaylist> spotifyPlaylists;

    public List<SpotifyPlaylist> getSpotifyPlaylists() {
        return spotifyPlaylists;
    }
}
