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
                            for (DocumentSnapshot document : task.getResult()) {
                                comparisonFromDocument = document.getId();
                                if (comparisonFromDocument == phereName) {
                                    Toast.makeText(JoinPhereActivity.this, "It Exists", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(JoinPhereActivity.this, "It Doesn't Exist", Toast.LENGTH_SHORT).show();
                                }
                                Log.d(TAG, document.getId() + " => " + phereName);
                            }
                        }
                    }
                });
//                db.collection(pheresCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//                DatabaseReference root = FirebaseDatabase.getInstance().getReference();
//                //DatabaseReference pheres = root.child(pheresCollection);
//                root.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.child(phereName).exists()){
//                            Toast.makeText(JoinPhereActivity.this, "It Exists", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Log.d(TAG, "onDataChange: " + phereName);
//                            Toast.makeText(JoinPhereActivity.this, "It Doesn't Exist", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
                //Doing a Query to check if the Phere Exists
//                DocumentReference documentReference = db.collection(pheresCollection).document(phereName);
//                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot documentSnapshot = task.getResult();
//                        if (documentSnapshot.exists()){
//                            Toast.makeText(JoinPhereActivity.this, "It Exists", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(JoinPhereActivity.this, "It Doesn't Exist", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
    }
}
