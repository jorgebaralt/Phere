package com.phereapp.phere;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phereapp.phere.home.SearchJoinPartiesFragment;
import com.phereapp.phere.login.StartActivity;

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

    //Bottom navigation view!
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //TODO: load new fragment
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_parties:
                    setTitle("Search Join Parties");
                    SearchJoinPartiesFragment fragment = new SearchJoinPartiesFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                            beginTransaction();
                    fragmentTransaction.replace(R.id.frame_fragment_home,fragment,"Search Join Parties")
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    //Loads before OnCreate
    @Override
    protected void onStart() {
        startIntent = new Intent(this,StartActivity.class);
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

        //get rid of title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_user);

        //layout views initialization
        mTextMessage = (TextView) findViewById(R.id.message);
        //mUsernameDisplay = (TextView) findViewById(R.id.txt_username_home);

        //set all views.
        //mUsernameDisplay.setText(mUsername);
        startIntent = new Intent(this,StartActivity.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initialize all Firebase instances        mAuth = FirebaseAuth.getInstance();
    }


    //create menu button on top right.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings,menu);
        return true;
    }

    //when an option is selected from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_logout:
                //sign out
                Log.d(TAG, "onOptionsItemSelected: Logging out...");
                FirebaseAuth.getInstance().signOut();
                startActivity(startIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //update to current user information. from database or authentication info.
    private void updateUI(FirebaseUser user){
        //get the current user information from the authenticated user.
        mUsername = user.getDisplayName();
        mEmail = user.getEmail();
        Log.d(TAG, "USER LOGGED IN => updateUI: username = " + mUsername + " email = " + mEmail );
        //set name on home
        //mUsernameDisplay.setText(mUsername);

    }
}
