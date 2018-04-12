package com.phereapp.phere.selected_phere;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;
import com.phereapp.phere.dialog_fragments.TimePickerFragment;
import com.phereapp.phere.pojo.Phere;

import java.util.HashMap;
import java.util.Map;

public class SelectedPhereSettingsActivity extends AppCompatActivity implements TimePickerFragment.TimeFromOnTimeSet {
    private static final String TAG = "SelectedPhereSettings";
    private Phere selectedPhere;
    private android.support.v7.widget.Toolbar mToolbar;
    private Button mDeletePhere, mSetTime;
    private String phereName, imageURL;
    //Firebase
    private FirebaseFirestore db;
    private StorageReference mStorageReference;
    private String pheresCollection = "pheres";

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
        mSetTime = findViewById(R.id.btn_setTime_selectedPhere_settings);

        mToolbar.setTitle(selectedPhere.getDisplayPhereName());

        mDeletePhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePhere();
            }
        });

        mSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
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

    @Override
    public void timeFromOnTimeSet(String time) {
        Log.d(TAG, "timeFromOnTimeSet: time =" + time);
        Map<String, Object> data = new HashMap<>();
        data.put("time", time);
        db.collection(pheresCollection).document(selectedPhere.getPhereName()).set(data, SetOptions.merge());
    }
}