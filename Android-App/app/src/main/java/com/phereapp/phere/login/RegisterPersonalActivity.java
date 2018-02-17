package com.phereapp.phere.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;
import com.phereapp.phere.User;

public class RegisterPersonalActivity extends AppCompatActivity {

    private final static String TAG = "RegisterPersonal";
    private final String accountType = "personal";

    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private Button mCreateAccountBtn;
    private FirebaseAuth mAuth;
    private String email,password,username;

    private FirebaseFirestore db;
    private FirebaseUser userCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_personal);

        mEmail = (EditText) findViewById(R.id.editTxt_email_personal);
        mUsername = (EditText) findViewById(R.id.editTxt_username_personal);
        mPassword = (EditText) findViewById(R.id.editTxt_password_personal);
        mCreateAccountBtn = (Button) findViewById(R.id.btn_createAccount_personal);

        //initialize firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // On click of Create Account Button
        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: check for empty email and password, do also a trim for blank spaces.

                //get the information of the user registering to store in db
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                username = mUsername.getText().toString();

                Log.d(TAG, "onClick:    " +
                        " email: " + mEmail +
                        " username: "+ mUsername
                    );

                //add user to Authentication in Firebase
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterPersonalActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   Log.d(TAG, "onComplete: Success creating user");

                                   //Add to database new user, with email, username and accountType
                                   addUserReference();
                                   //TODO: send email verification ** Manage User on Docs **

                               }else {
                                   Log.w(TAG, "onComplete: Error creating user ", task.getException());
                                   Toast.makeText(RegisterPersonalActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                               }
                            }
                        });




            }
        });
    }

    private void addUserReference(){
        //create new user object to sent to db
        User newUser = new User(email,username,accountType);
        // create a document entry into the database, with the name of the email of the user.
        db.collection("users").document(email).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: User added");
                userCreated = FirebaseAuth.getInstance().getCurrentUser();
                if(userCreated != null){
                    UserProfileChangeRequest setUsername = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();

                    userCreated.updateProfile(setUsername)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "onComplete: DisplayName created. \n Moving on to Home page...");

                                    Intent mainIntent = new Intent(RegisterPersonalActivity.this, MainActivityUser.class);
                                    startActivity(mainIntent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error creating the Displayname", e.fillInStackTrace());
                        }
                    });
                }else{
                    Log.d(TAG, "onSuccess: user created is null");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Trouble adding user");
            }
        });
        
    }

}

