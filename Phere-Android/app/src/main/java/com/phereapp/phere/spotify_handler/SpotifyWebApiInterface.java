package com.phereapp.phere.spotify_handler;

import com.phereapp.phere.pojo.SpotifyUser;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpotifyWebApiInterface {
    //Get current user profile
    @GET("/v1/me")
    Call<SpotifyUser> getSpotifyUser();
}
