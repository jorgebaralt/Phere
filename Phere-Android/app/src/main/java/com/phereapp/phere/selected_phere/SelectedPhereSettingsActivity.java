package com.phereapp.phere.selected_phere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereSettingsActivity extends AppCompatActivity {
    private static final String TAG = "SelectedPhereSettings";
    private Phere selectedPhere;
    private android.support.v7.widget.Toolbar mToolbar;
    private Button mDeletePhere;
    //Firebase
    private FirebaseFirestore db;
    private StorageReference mStorageReference;
    private String pheresCollection = "pheres";
    private String phereName, imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_settings);

        db = FirebaseFirestore.getInstance();
        selectedPhere = (Phere) SelectedPhereSettingsActivity.this.getIntent().getSerializableExtra("SelectedPhere");
        phereName = selectedPhere.getPhereName();
        imageURL = selectedPhere.getImageURL();

        mDeletePhere = findViewById(R.id.btn_deletePhere_selectedPhere);
        mToolbar = findViewById(R.id.toolbar_selectePhere_settings);
        mToolbar.setTitle(selectedPhere.getDisplayPhereName());

        mDeletePhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePhere();
            }
        });
    }

    private void deletePhere() {
        if (imageURL != null) {
            //Getting the reference from the URL stored in the Database to delete the Image in the Storage
            mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageURL);
            mStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //after the Image got deleted from the Storage, start deleting the phere from the Database
                    db.collection(pheresCollection).document(phereName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SelectedPhereSettingsActivity.this, "Phere Deleted", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(SelectedPhereSettingsActivity.this, MainActivityUser.class);
                            startActivity(homeIntent);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: The Image could not be Deleted", e.getCause());
                }
            });

        } else {
            db.collection(pheresCollection).document(phereName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SelectedPhereSettingsActivity.this, "Phere Deleted", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(SelectedPhereSettingsActivity.this, MainActivityUser.class);
                    startActivity(homeIntent);
                }
            });
        }
    }

}
