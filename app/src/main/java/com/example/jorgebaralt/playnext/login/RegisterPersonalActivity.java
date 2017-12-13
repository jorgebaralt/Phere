package com.example.jorgebaralt.playnext.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.jorgebaralt.playnext.R;

public class RegisterPersonalActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private Button mCreateAccountBtn;
    private final static String TAG = "RegisterPersonal";
    private String accountType = "personal";

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

        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:    " +
                        " email: " + mEmail.getText().toString() +
                        " username: "+ mUsername.getText().toString()
                    );
                //add user to database
                FirebaseRegistration.createUser(mEmail.getText().toString(),mUsername.getText().toString(),mPassword.getText().toString(),accountType);
            }
        });
    }

}
