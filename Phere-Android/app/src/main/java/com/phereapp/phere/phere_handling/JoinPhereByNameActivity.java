package com.phereapp.phere.phere_handling;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

import java.util.HashMap;
import java.util.Map;

public class JoinPhereByNameActivity extends AppCompatActivity {
    private static String TAG = "JoinPhereByNameActivity: ";

    private Button mJoinPhereByName;
    private Button mFindPublicPhere;
    private EditText mPhereNameEditText;
    private String phereName;
    private final String phereCollection = "pheres";
    private final String memberCollection = "members";

    // firebase
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_join_phere_by_name);

        mJoinPhereByName = (Button) findViewById(R.id.btn_join_phere_byName);
        mFindPublicPhere = (Button) findViewById(R.id.btn_find_publicPheres);
        mPhereNameEditText = (EditText) findViewById(R.id.editTxt_phereName_joinPhere);
        //firebase
        db = FirebaseFirestore.getInstance();

        mFindPublicPhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent publicPhereIntent = new Intent(JoinPhereByNameActivity.this, JoinablePublicPheres.class);
                startActivity(publicPhereIntent);
            }
        });


        // On click of the Join button
        mJoinPhereByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phereName = mPhereNameEditText.getText().toString();
                Log.d(TAG, "onClick: Phere Name = " +phereName);
                //look for the specific Phere name
                db.collection(phereCollection).document(phereName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            //assign the object
                            Phere phere = documentSnapshot.toObject(Phere.class);
                            //add the current member to the phere.
                            addMemberToPhere(phere);
                        }else {
                            Toast.makeText(JoinPhereByNameActivity.this, "Phere does not Exist!.", Toast.LENGTH_SHORT).show();
                        }
              }
                });


            }
        });
    }

    //add member
    private void addMemberToPhere(Phere phere) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //make sure user is logged in
        if(currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null) {
                DocumentReference memberRef = db
                        .collection(phereCollection).document(phere.getPhereName())
                        .collection(memberCollection).document(userEmail);
                //fields for new member
                //TODO: What else can go here?
                Map<String,Object> member = new HashMap<>();
                member.put("username",currentUser.getDisplayName());
                memberRef.set(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(JoinPhereByNameActivity.this, "You have Joined this Phere, Enjoy..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(JoinPhereByNameActivity.this, "Error joining Phere...", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: Error... ",e.fillInStackTrace() );
                    }
                });
            }
        }




    }
}