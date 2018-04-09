package com.phereapp.phere.spotify_handler;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.phereapp.phere.api.ApiInterface;
import com.phereapp.phere.api.SpotifyTokenApiClient;
import com.phereapp.phere.helper.ActivityResultHandler;
import com.phereapp.phere.helper.SharedPreferencesHelper;
import com.phereapp.phere.pojo.SpotifyToken;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpotifyHandler implements SpotifyPlayer.NotificationCallback,ConnectionStateCallback,ActivityResultHandler {


    public static String CLIENT_ID = "c8258d2a53aa40738210728a55a3d001";
    public static String CLIENT_SECRET = "2b252c0e6c8c4217a8dcf3b0cee29f3b";
    //TODO : fix uri
    public static String REDIRECT_URI = "http://phere.com/callback";
    private static String REFRESH_TYPE = "refresh_token";
    private static String TAG="SpotifyHandler";


    public final String AUTHORIZATION_TYPE = "authorization_code";
    private static final int REQUEST_CODE = 1337;
    public static String token;

    public static String code;
    private Activity activity;
    private String scopes[] = new String[]{"streaming","playlist-read-private"};
    public static SpotifyToken spotifyToken;

    public SpotifyHandler(Activity activity){
        this.activity = activity;
    }

    public void performLogin(){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (
                        CLIENT_ID,
                        AuthenticationResponse.Type.CODE,
                        REDIRECT_URI
                );
        builder.setScopes(scopes);
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(activity,REQUEST_CODE,request);
    }
    public void performLogout(){
        AuthenticationClient.stopLoginActivity(activity,123);
    }

    @Override
    public void onLoggedIn() {
        Log.d(TAG, "onLoggedIn: User has logged in ");
        //TODO : display who logged in ( email ) on the logs.
        //mPlayer.playUri(null,"spotify:track:2TpxZ7JUBn3uw46aR7qd6V", 0, 0);
    }

    @Override
    public void onLoggedOut() {
        Log.d(TAG, "onLoggedOut: user logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d(TAG, "onLoginFailed: logging failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d(TAG, "onTemporaryError: temp error");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d(TAG, "onConnectionMessage: received connection message" + s);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d(TAG, "onPlaybackEvent: Playback event receive: " + playerEvent.name() );
        switch (playerEvent){
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d(TAG, "onPlaybackError: ");

    }


    @Override
    public void activityResultHandler(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode,data);

            //TODO: this must be done on our own api (BUILT ON NODE.JS)
            if(response.getType() == AuthenticationResponse.Type.CODE){
                Log.d(TAG, " Spotify code = " + response.getCode());
                code = response.getCode();
                //use the code to get access token and refresh token
                ApiInterface spotifyInterface = SpotifyTokenApiClient.getApiClient().create(ApiInterface.class);
                Call<SpotifyToken> call = spotifyInterface.getSpotifyToken(AUTHORIZATION_TYPE,code,REDIRECT_URI,CLIENT_ID,CLIENT_SECRET);
                call.enqueue(new Callback<SpotifyToken>() {
                    @Override
                    public void onResponse(Call<SpotifyToken> call, Response<SpotifyToken> response) {
                        if(response.isSuccessful()){
                            spotifyToken = response.body();
                            Log.d(TAG, "onResponse: spotify token = " + spotifyToken.getAccessToken() + " refresh token = " + spotifyToken.getRefreshToken() + "");
                            SharedPreferencesHelper.setDefaults("spotifyToken",spotifyToken.getAccessToken(),activity);
                            SharedPreferencesHelper.setDefaults("refreshToken",spotifyToken.getRefreshToken(),activity);
                        }
                        else{
                            Log.d(TAG, "onResponse: response = " + response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<SpotifyToken> call, Throwable t) {
                        Log.d(TAG, "onFailure: Error getting spotify token" + t.getMessage());
                    }
                });

                token = response.getAccessToken();
            }
        }
    }

    public void refreshToken(){

        String refreshToken = SharedPreferencesHelper.getDefaults("refreshToken",activity);

        ApiInterface spotifyInterface = SpotifyTokenApiClient.getApiClient().create(ApiInterface.class);
        Call<SpotifyToken> call = spotifyInterface.refreshSpotifyToken(REFRESH_TYPE,refreshToken,CLIENT_ID,CLIENT_SECRET);
        call.enqueue(new Callback<SpotifyToken>() {
            @Override
            public void onResponse(Call<SpotifyToken> call, Response<SpotifyToken> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Token has been refreshed");
                    spotifyToken = response.body();
                    SharedPreferencesHelper.setDefaults("spotifyToken",spotifyToken.getAccessToken(),activity);
                }
                else {
                    Log.d(TAG, "onResponse: response = " + response.body());
                }
            }

            @Override
            public void onFailure(Call<SpotifyToken> call, Throwable t) {

            }
        });
    }
}
