package com.phereapp.phere.spotify_handler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.phereapp.phere.R;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class SpotifyLoginActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback,ConnectionStateCallback {

    private static String TAG = "SpotifyLoginActivity";
    private static final String CLIENT_ID = "c8258d2a53aa40738210728a55a3d001";
    private static final String REDIRECT_URI = "http://phere.com/callback/";
    private Player mPlayer;
    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_login);

        //Start Spotify Login, result delivered to onActivityResult =>
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder
                (
                        CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI
                );
        builder.setScopes(new String[]{"user-read-private","streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this,REQUEST_CODE,request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //check the result coming from
        if(requestCode == REQUEST_CODE){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode,intent);
            if(response.getType() == AuthenticationResponse.Type.TOKEN){
                Config playerConfig = new Config(this,response.getAccessToken(),CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        Log.d(TAG, "onInitialized: Initializing PLayer...");
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(SpotifyLoginActivity.this);
                        mPlayer.addNotificationCallback(SpotifyLoginActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError: " + "Could not initialize player" + throwable.getMessage());
                    }
                });
            }
        }

    }

    @Override
    public void onLoggedIn() {
        Log.d(TAG, "onLoggedIn: user logged in ");
        mPlayer.playUri(null,"spotify:track:2TpxZ7JUBn3uw46aR7qd6V", 0, 0);
    }

    @Override
    public void onLoggedOut() {
        Log.d(TAG, "onLoggedOut: uyer logged out");
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
    //to destroy the player
    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
