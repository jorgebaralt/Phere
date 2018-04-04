package com.phereapp.phere.api;

import com.phereapp.phere.pojo.SpotifyUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterface {
    //Get current user profile
    @GET("/v1/me")
    Call<SpotifyUser> getSpotifyUser(@Header("Authorization")String token);

}
