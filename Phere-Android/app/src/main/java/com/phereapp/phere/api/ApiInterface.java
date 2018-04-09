package com.phereapp.phere.api;

import com.phereapp.phere.pojo.SpotifyPlaylistList;
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

    //get token at login
    @FormUrlEncoded
    @POST("/api/token")
    Call<SpotifyToken> getSpotifyToken(
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    //request token refresh
    @FormUrlEncoded
    @POST("/api/token")
    Call<SpotifyToken> refreshSpotifyToken(
            @Field("grant_type") String grantType,
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret

    );

    //getCurrentUserPlaylists
    @GET("/v1/me/playlists")
    Call<SpotifyPlaylistList> getSpotifyPlaylists(@Header("Authorization")String token);
}
