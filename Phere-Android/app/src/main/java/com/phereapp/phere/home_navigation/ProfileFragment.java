package com.phereapp.phere.home_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.phereapp.phere.R;
import com.phereapp.phere.login.StartLoginActivity;
import com.phereapp.phere.spotify_handler.SpotifyHandler;
import com.spotify.sdk.android.player.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button logoutBtn;
    private Button spotifyBtn;
    private TextView spotifyLogout;
    private String TAG = "ProfileFragment";
    //Facebook
    private CallbackManager mCallbackManager;
    //Firebase
    private FirebaseAuth mAuth;
    //Spotify
    private static final String CLIENT_ID = "c8258d2a53aa40738210728a55a3d001";
    private static final String REDIRECT_URI = "http://phere.com/callback/";
    private Player mPlayer;
    private static final int REQUEST_CODE = 1337;
    SpotifyHandler spotifyHandler;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize
        logoutBtn = rootView.findViewById(R.id.btn_logout_profile);
        mAuth = FirebaseAuth.getInstance();
        spotifyBtn = rootView.findViewById(R.id.btn_spotify_profile);
        spotifyHandler = new SpotifyHandler(getActivity());
        spotifyLogout = rootView.findViewById(R.id.logout_spotify_profile);

        //Logout Button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: logging out...");
                FirebaseAuth.getInstance().signOut();
                //handles Facebook logout
                LoginManager.getInstance().logOut();
                //move to start intent for logins options
                Intent startIntent = new Intent(getActivity(),StartLoginActivity.class);
                startActivity(startIntent);
                getActivity().finish();
            }
        });

        //TODO: make an api call to check if user is still logged it: https://api.spotify.com/v1/me
        //TODO: to decide which button to show (login or logout)

        //login to spotify
        spotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotifyHandler.performLogin();

            }
        });
        spotifyLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotifyHandler.performLogout();
            }
        });

         //Initialize Facebook button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = rootView.findViewById(R.id.btn_facebook_link_accounts);
        loginButton.setFragment(this);
        loginButton.setText("Link With Facebook");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                linkAccountWithCredentials(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "facebook:onError", error);
            }
        });

        //Handle FAB

        return rootView;
    }

    private void linkAccountWithCredentials(AccessToken token){
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //FACEBOOK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        //SPOTIFY
        spotifyHandler.activityResultHandler(requestCode,resultCode,data);


    }



}
