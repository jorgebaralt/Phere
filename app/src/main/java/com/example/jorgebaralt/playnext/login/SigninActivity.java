package com.example.jorgebaralt.playnext.login;

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

import com.example.jorgebaralt.playnext.MainActivityUser;
import com.example.jorgebaralt.playnext.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SigninActivity extends AppCompatActivity {

    private Button mSigninBtn;
    private EditText mEmail;
    private EditText mPassword;
    private TextView mForgotPassBtn;
    private String email,password;
    //Firebase Variables
    final private static String TAG = "SigninActivity";
    private FirebaseAuth currentAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        mSigninBtn = (Button) findViewById(R.id.btn_signin_signin);
        mEmail = (EditText) findViewById(R.id.editTxt_email_signin);
        mPassword = (EditText) findViewById(R.id.editTxt_password_signin);
        mForgotPassBtn = (TextView) findViewById(R.id.btn_forgotpass_signin);
        currentAuth = FirebaseAuth.getInstance();

        mSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: check for empty email and password, do also a trim for blank spaces.
                //get the email and password when we click.
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                //firebase Auth instance from mainActivity
                currentAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "createUserWithEmail: Success ");
                                    //move to main activity (it handles the update UI)
                                    Intent mainIntent = new Intent(SigninActivity.this, MainActivityUser.class);
                                    startActivity(mainIntent);
                                    finish();

                                }else{
                                    Log.w(TAG, "createUserWithEmail: Failure ",task.getException());
                                    Toast.makeText(SigninActivity.this, "Authentication Failed, try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
