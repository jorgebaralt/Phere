package com.phereapp.phere.phereHandling;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phereapp.phere.Phere;
import com.phereapp.phere.R;
import com.phereapp.phere.User;

public class CreateNewPhereActivity extends AppCompatActivity {

    private final static String TAG = "CreateNewPhere";
    private final String phereType = "privacy";

    private EditText mPhereName;
    private EditText mPhereLocation;
    private RadioGroup mPrivacy;
    private RadioButton mPrivacyChoosen;
    private Button mDisplay;
    private String choosenPrivacy;
    private Button mCreatePhereButton;
    private String phereName, phereLocation;
    private String pheresCollection = "pheres";
    private FirebaseFirestore db;
    private FirebaseUser phereCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_create_new_phere);

        mPhereName = (EditText) findViewById(R.id.editTxt_phereName_createPhere);
        mPhereLocation = (EditText) findViewById(R.id.editTxt_location_createPhere);
        mCreatePhereButton = (Button) findViewById(R.id.btn_ok_create_phere);

        db = FirebaseFirestore.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        mPrivacy = (RadioGroup) findViewById(R.id.radio_choose_createPhere);

        mCreatePhereButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                phereName = mPhereName.getText().toString();
                phereLocation = mPhereLocation.getText().toString();

                int selectedId = mPrivacy.getCheckedRadioButtonId();
                mPrivacyChoosen = (RadioButton) findViewById(selectedId);
                choosenPrivacy = mPrivacyChoosen.getText().toString();

                addUserReference();
            }
        });

    }

    public void addUserReference(){
        Phere newPhere = new Phere(phereName, phereLocation, choosenPrivacy);
        db.collection(pheresCollection).document(phereName).set(newPhere);
    }


}


