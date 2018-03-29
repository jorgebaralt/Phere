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
import com.phereapp.phere.MapView;
import com.phereapp.phere.R;
import com.phereapp.phere.login.StartLoginActivity;
import com.phereapp.phere.spotify_handler.SpotifyLoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button logoutBtn;
    private Button spotifyBtn;
    // Testing the map
    private Button mapTest;
    private String TAG = "ProfileFragment";
    //Facebook
    private CallbackManager mCallbackManager;
    //Firebase
    private FirebaseAuth mAuth;


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


        //login to spotify
        spotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spotifyIntent = new Intent(getActivity(), SpotifyLoginActivity.class);
                startActivity(spotifyIntent);
            }
        });

         //Initialize Facebook button

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = rootView.findViewById(R.id.btn_facebook_link_accounts);
        loginButton.setFragment(this);
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

        // Testing the Map
        mapTest = rootView.findViewById(R.id.btn_mapTest);
        mapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: trying map");
                Intent mapTestIntent = new Intent (getActivity(), MapView.class);
                startActivity(mapTestIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

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
}
