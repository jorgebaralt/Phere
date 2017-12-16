package com.example.jorgebaralt.playnext.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.jorgebaralt.playnext.R;

public class StartActivity extends AppCompatActivity {

    private Button mSignupBtn;
    private Button mSigninBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        mSignupBtn = (Button) findViewById(R.id.btn_email_signup);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(StartActivity.this,SelectActivity.class);
                startActivity(registerIntent);
            }
        });

        mSigninBtn = (Button) findViewById(R.id.btn_email_signin);
        mSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SigninIntent = new Intent(StartActivity.this,SigninActivity.class);
            }
        });

    }
}
