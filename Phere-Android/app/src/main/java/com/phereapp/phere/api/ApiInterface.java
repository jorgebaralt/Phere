package com.phereapp.phere.api;

import com.phereapp.phere.pojo.SpotifyToken;
import com.phereapp.phere.pojo.SpotifyUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    //Get current user profile
    @GET("/v1/me")
    Call<SpotifyUser> getSpotifyUser(@Header("Authorization")String token);

    @FormUrlEncoded
    @POST("/api/token")
    Call<SpotifyToken> getSpotifyToken(
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );



}
