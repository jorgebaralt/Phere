package com.example.jorgebaralt.playnext.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.jorgebaralt.playnext.R;



public class StartActivity extends AppCompatActivity {
    private Button mSignupBtn;
    private TextView mSigninBtn;
    final private static String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        mSignupBtn = (Button) findViewById(R.id.btn_email_start);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Register Personal Account clicked");
                Intent registerIntent = new Intent(StartActivity.this,SelectActivity.class);
                startActivity(registerIntent);
            }
        });

        mSigninBtn = (TextView) findViewById(R.id.btn_signin_start);
        mSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sign in clicked");
                Intent signinIntent = new Intent(StartActivity.this,SigninActivity.class);
                startActivity(signinIntent);
            }
        });

    }
}
