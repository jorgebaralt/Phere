package com.phereapp.phere;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phereapp.phere.helper.BottomNavigationViewHelper;
import com.phereapp.phere.home_navigation.CreateJoinPheresFragment;
import com.phereapp.phere.home_navigation.HomeNewsFragment;
import com.phereapp.phere.home_navigation.NotificationsFragment;
import com.phereapp.phere.home_navigation.ProfileFragment;
import com.phereapp.phere.login.StartLoginActivity;

public class MainActivityUser extends AppCompatActivity {

    private final static String TAG = "MainActivityUser";
    private TextView mTextMessage;
    //private TextView mUsernameDisplay;
    private String mUsername;
    private String mEmail;
    private Intent startIntent;
    private FirebaseUser currentUser;
    //Firebase Variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    //Facebook Variables
    private AuthCredential credential;



    //Bottom navigation view!
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home News Pheres");

                    HomeNewsFragment homeNewsFragment = new HomeNewsFragment();
                    FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionHome.replace(R.id.frame_fragment_home,homeNewsFragment,"Home news Fragment").commit();

                    return true;
                case R.id.navigation_newphere:
                    setTitle("Search Join Pheres");
                    CreateJoinPheresFragment createJoinPheresFragment = new CreateJoinPheresFragment();
                    FragmentTransaction fragmentTransactionNewParties = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionNewParties.replace(R.id.frame_fragment_home, createJoinPheresFragment,"Search Join Parties Fragment").commit();
                    return true;
                case R.id.navigation_notifications:
                    setTitle("Notifications");
                    NotificationsFragment notificationsFragment = new NotificationsFragment();
                    FragmentTransaction fragmentTransactionNotification = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionNotification.replace(R.id.frame_fragment_home,notificationsFragment,"Notifications Fragment").commit();
                    return true;
                case R.id.navigation_profile:
                    setTitle("Profile");
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransactionMyParties = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionMyParties.replace(R.id.frame_fragment_home, profileFragment,"My Parties").commit();
                    return true;
            }
            return false;
        }
    };


    //Loads before OnCreate
    @Override
    protected void onStart() {
        startIntent = new Intent(this,StartLoginActivity.class);
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Log.d(TAG, "onStart: user: " + currentUser.getEmail() + " is logged in");
            //get the database
            db = FirebaseFirestore.getInstance();
            //update the ui to display the name of current user.
            updateUI(currentUser);
        }else {
            Log.d(TAG, "onStart: user is not logged in, Move to login Activity");
            startActivity(startIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get rid of notifications
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_user);

        //user helper class to make bottom navigation static
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        //move instantly to Home Fragment
        HomeNewsFragment homeNewsFragment = new HomeNewsFragment();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame_fragment_home,homeNewsFragment,"Home news Fragment").commit();

        //layout views initialization
        mTextMessage = (TextView) findViewById(R.id.message);
        //mUsernameDisplay = (TextView) findViewById(R.id.txt_username_home);

        //set all views.
        //mUsernameDisplay.setText(mUsername);
        startIntent = new Intent(this,StartLoginActivity.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initialize all Firebase instances        mAuth = FirebaseAuth.getInstance();
    }

    //update to current user information. from database or authentication info.
    public void updateUI(FirebaseUser user){
        //get the current user information from the authenticated user.
        mUsername = user.getDisplayName();
        mEmail = user.getEmail();
        Log.d(TAG, "USER LOGGED IN => updateUI: username = " + mUsername + " email = " + mEmail );
        //set name on home
        //mUsernameDisplay.setText(mUsername);

    }
    //call all the onActivityResult on each fragment.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
