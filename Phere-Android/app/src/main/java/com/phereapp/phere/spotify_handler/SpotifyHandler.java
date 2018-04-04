package com.phereapp.phere.spotify_handler;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.phereapp.phere.helper.ActivityResultHandler;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class SpotifyHandler implements SpotifyPlayer.NotificationCallback,ConnectionStateCallback,ActivityResultHandler {

    public static String CLIENT_ID = "c8258d2a53aa40738210728a55a3d001";
    public static String REDIRECT_URI = "http://phere.com/callback/";
    public static String token;

    private static final int REQUEST_CODE = 1337;
    private String TAG = "SpotifyHandler";
    private Activity activity;
    private String scopes[] = new String[]{"streaming"};
    public SpotifyHandler(Activity activity){
        this.activity = activity;
    }
    public void performLogin(){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (
                        CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
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
            if(response.getType() == AuthenticationResponse.Type.TOKEN){
                //we got access token,
                Log.d(TAG, "activityResultHandler: token  =  " + response.getAccessToken() + " type = " + response.getType());
                token = response.getAccessToken();
            }
        }
    }
}
