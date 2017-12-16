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
import android.widget.Toast;

import com.example.jorgebaralt.playnext.MainActivityUser;
import com.example.jorgebaralt.playnext.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPersonalActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private Button mCreateAccountBtn;
    private final static String TAG = "RegisterPersonal";
    private String accountType = "personal";
    private FirebaseAuth mAuth;
    private String email,password;

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
        mAuth = FirebaseAuth.getInstance();


        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

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
                                   FirebaseUser user = mAuth.getCurrentUser();
                                   Intent mainIntent = new Intent(RegisterPersonalActivity.this, MainActivityUser.class);
                                   startActivity(mainIntent);
                               }else {
                                   Log.w(TAG, "onComplete: Error creating user ", task.getException());
                                   Toast.makeText(RegisterPersonalActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                               }
                            }
                        });

                //TODO: Add to database new user, with username and accountType

            }
        });
    }

}

