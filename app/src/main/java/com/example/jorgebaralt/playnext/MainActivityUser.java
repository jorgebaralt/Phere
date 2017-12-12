package com.example.jorgebaralt.playnext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jorgebaralt.playnext.login.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityUser extends AppCompatActivity {

    private final static String TAG = "MainActivityUser";
    private TextView mTextMessage;
    private TextView mUsernameDisplay;
    private String mUsername;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //TODO: load new fragment
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    //Firebase Variables
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout views initialization
        mTextMessage = (TextView) findViewById(R.id.message);
        mUsernameDisplay = (TextView) findViewById(R.id.username_home);

        mUsernameDisplay.setText(mUsername);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initialize all Firebase instances
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser.getDisplayName());
        }else {
            Log.d(TAG, "onStart: user is not logged in, Move to login Activity");
            Intent loginIntent = new Intent(this,StartActivity.class);
            startActivity(loginIntent);
        }
        
    }

    private void updateUI(String username){
        mUsername = username;
    }
}
