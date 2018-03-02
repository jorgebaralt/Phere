package com.phereapp.phere.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.phereapp.phere.R;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private EditText mEmail;
    private Button sendEmailbtn;
    private String email;
    final private static String TAG = "PswRecoveryActivity";
    // Firebase
    private FirebaseAuth currentAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_password_recovery);

        currentAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.editText_email_input_recoveryPsw);
        sendEmailbtn = (Button) findViewById(R.id.btn_sendPsw_email);

        sendEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                currentAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "onComplete: Password Recovery Email sent");
                                    Toast.makeText(PasswordRecoveryActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Intent SigninIntent = new Intent (PasswordRecoveryActivity.this, SigninActivity.class);
                startActivity(SigninIntent);
            }
        });
    }
}
