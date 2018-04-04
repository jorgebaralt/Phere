package com.phereapp.phere.selected_phere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.phereapp.phere.R;
import com.phereapp.phere.pojo.SpotifyUser;
import com.phereapp.phere.spotify_handler.SpotifyHandler;
import com.phereapp.phere.api.SpotifyWebApiClient;
import com.phereapp.phere.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedPherePlaylistActivity extends AppCompatActivity {
    private String TAG = "SelectedPherePlaylistActivity";
    private SpotifyUser spotifyUser;
    private ApiInterface spotifyInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_playlist);

        spotifyInterface = SpotifyWebApiClient.getApiClient().create(ApiInterface.class);
        Call<SpotifyUser> call = spotifyInterface.getSpotifyUser("Bearer " + SpotifyHandler.token);
        call.enqueue(new Callback<SpotifyUser>() {
            @Override
            public void onResponse(Call<SpotifyUser> call, Response<SpotifyUser> response) {
                Log.d(TAG, "onResponse: Making call to spotify");
                if(response.isSuccessful()) {
                    spotifyUser = response.body();
                    Log.d(TAG, "onResponse: SpotifyUser = " + spotifyUser.getDisplayName());
                }else{
                    Log.e(TAG, "onResponse: Spotify Called but was not successful, response = " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<SpotifyUser> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t.fillInStackTrace() );
            }
        });
    }
}
