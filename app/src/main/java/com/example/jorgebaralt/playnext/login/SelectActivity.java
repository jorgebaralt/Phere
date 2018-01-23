package com.example.jorgebaralt.playnext.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.jorgebaralt.playnext.R;

public class SelectActivity extends AppCompatActivity {
    //buttons
    private Button mPersonalButton;
    private Button mBusinessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select);

        mPersonalButton = (Button) findViewById(R.id.btn_personal_select);
        mBusinessButton = (Button) findViewById(R.id.btn_business_select);

        //Personal button clicked
        mPersonalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerPersonalIntent = new Intent(SelectActivity.this,RegisterPersonalActivity.class);
                startActivity(registerPersonalIntent);

            }
        });

        //Business button clicked
        mBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectActivity.this, "Business accounts in Progress...", Toast.LENGTH_LONG).show();
            }
        });

    }
}
