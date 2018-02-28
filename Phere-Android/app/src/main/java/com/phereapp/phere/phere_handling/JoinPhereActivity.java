package com.phereapp.phere.phere_handling;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.phereapp.phere.R;

import java.util.Collection;
import java.util.Objects;

public class JoinPhereActivity extends AppCompatActivity {

    private Button mJoinPhereByName;
    private Button mFindPublicPhere;
    private EditText mPhereName;
    private String pheresCollection = "pheres";
    private String phereName;
    private static String TAG = "JoinPhereActivity: ";
    private String comparisonFromDocument;
    private Boolean phereExists;
    // firebase
    private FirebaseFirestore db;


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


        // On click of the Join button
        mJoinPhereByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting the Input of the user
                phereName = mPhereName.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(pheresCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            // Iterating through all the documents
                            for (DocumentSnapshot document : task.getResult()) {
                                // Making the ID of the document found a string to compare
                                comparisonFromDocument = document.getId();
                                // Comparing the document ID and the user Input
                                if (comparisonFromDocument.equals(phereName)) {
                                    phereExists = true;
                                    break;
                                }
                                else {
                                    phereExists = false;
                                }

                                Log.d(TAG, comparisonFromDocument + " => " + phereName);

                            }

                            if (phereExists){
                                Toast.makeText(JoinPhereActivity.this, "It Exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(JoinPhereActivity.this, "It Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });
    }
}
