package com.example.jorgebaralt.playnext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jorgebaralt.playnext.login.StartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivityUser extends AppCompatActivity {

    private final static String TAG = "MainActivityUser";
    private TextView mTextMessage;
    private TextView mUsernameDisplay;
    private static String mUsername;
    private Intent startIntent;

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



    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //get current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            db = FirebaseFirestore.getInstance();
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
        setContentView(R.layout.activity_main);

        //layout views initialization
        mTextMessage = (TextView) findViewById(R.id.message);
        mUsernameDisplay = (TextView) findViewById(R.id.txt_username_home);

        mUsernameDisplay.setText(mUsername);
        startIntent = new Intent(this,StartActivity.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initialize all Firebase instances
        mAuth = FirebaseAuth.getInstance();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings,menu);
        return true;
    }

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


    private void updateUI(FirebaseUser user){
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("email",user.getEmail()).limit(1);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}
