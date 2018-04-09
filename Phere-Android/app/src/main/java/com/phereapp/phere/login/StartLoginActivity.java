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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;


//choose whether to sign in or sign up.
public class StartLoginActivity extends AppCompatActivity {
    private Button mSignInBtn;
    private TextView mForgotPassword, mCreateAccount;
    private EditText mEmail, mPassword;
    private String userEmail, userPassword;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    final private static String TAG = "StartLoginActivity";
    //Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_start);

        mAuth = FirebaseAuth.getInstance();

        mForgotPassword = findViewById(R.id.txt_forgotPassword_loginScreen);
        mSignInBtn = findViewById(R.id.btn_logIn_loginScreen);
        mEmail = findViewById(R.id.editTxt_userEmail_loginScreen);
        mPassword = findViewById(R.id.editTxt_userPassword_loginScreen);
        mCreateAccount = findViewById(R.id.txt_btn_createAccount_loginScreen);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the email and password when we click.
                userEmail = mEmail.getText().toString();
                userPassword = mPassword.getText().toString();

                //firebase Auth instance from mainActivity
                if (userEmail.isEmpty() && userPassword.isEmpty()) {
                    Toast.makeText(StartLoginActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else if (userPassword.isEmpty()){
                    Toast.makeText(StartLoginActivity.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                } else if (userEmail.isEmpty()) {
                    Toast.makeText(StartLoginActivity.this, "Please Enter an Email", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(StartLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail: Success ");
                                        //move to main activity (it handles the update UI)
                                        Intent mainIntent = new Intent(StartLoginActivity.this, MainActivityUser.class);
                                        startActivity(mainIntent);
                                        finish();
                                    } else {
                                        Log.w(TAG, "createUserWithEmail: Failure ", task.getException());
                                        Toast.makeText(StartLoginActivity.this, "Authentication Failed, try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });



        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordRecoveryIntent = new Intent(StartLoginActivity.this, PasswordRecoveryActivity.class);
                startActivity(passwordRecoveryIntent);
            }
        });

        //if we are signing up, we have to go to select activity to pick (business or personal account)
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Register Personal Account clicked");
                Intent registerIntent = new Intent(StartLoginActivity.this, SelectActivity.class);
                startActivity(registerIntent);
            }
        });


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.btn_facebookLogin_start);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "facebook:onError", error);
                // ...
            }


        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void handleFacebookAccessToken(AccessToken token){
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Sign in success, Go to the next Activity (MainActivityUser)
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent mainActivityIntent = new Intent(StartLoginActivity.this, MainActivityUser.class);
                    startActivity(mainActivityIntent);
                }

                else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(StartLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
