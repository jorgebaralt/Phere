package com.phereapp.phere.phere_handling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.phereapp.phere.R;

public class JoinPhereActivity extends AppCompatActivity {

    private Button mJoinPhereByName;
    private Button mFindPublicPhere;
    private EditText mPhereName;
    private static String TAG = "JoinPhereActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_join_phere);

        mJoinPhereByName = (Button) findViewById(R.id.btn_join_phere_byName);
        mFindPublicPhere = (Button) findViewById(R.id.btn_find_publicPheres);
        mPhereName = (EditText) findViewById(R.id.editTxt_phereName_joinPhere);

        mFindPublicPhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent publicPhereIntent = new Intent(JoinPhereActivity.this, JoinablePublicPheres.class);
                startActivity(publicPhereIntent);
            }
        });
    }
}
