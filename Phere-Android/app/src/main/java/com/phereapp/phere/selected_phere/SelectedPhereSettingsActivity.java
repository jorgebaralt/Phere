package com.phereapp.phere.selected_phere;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;
import com.phereapp.phere.dialog_fragments.DressCodeDialogFragment;
import com.phereapp.phere.dialog_fragments.TimePickerFragment;
import com.phereapp.phere.pojo.Phere;

import java.util.HashMap;
import java.util.Map;

public class SelectedPhereSettingsActivity extends AppCompatActivity implements TimePickerFragment.TimeFromOnTimeSet, DressCodeDialogFragment.DressCodeSet {
    private static final String TAG = "SelectedPhereSettings";
    private Phere selectedPhere;
    private android.support.v7.widget.Toolbar mToolbar;
    private Button mDeletePhere, mSetTime, mDressCode;
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

        mDeletePhere = findViewById(R.id.btn_deletePhere_selectedPhere_settings);
        mToolbar = findViewById(R.id.toolbar_selectedPhere_settings);
        mSetTime = findViewById(R.id.btn_setTime_selectedPhere_settings);
        mDressCode = findViewById(R.id.btn_dressCode_selectedPhere_settings);

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent homeIntent = new Intent(SelectedPhereSettingsActivity.this, MainActivityUser.class);
                        startActivity(homeIntent);
                        deletePhere();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        mToolbar.setTitle(selectedPhere.getDisplayPhereName());

        mDeletePhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Delete Phere?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        mSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        mDressCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dressCodeFragment = new DressCodeDialogFragment();
                dressCodeFragment.show(getFragmentManager(), "DressCode");
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

    @Override
    public void dressCodeSet(String selectedDressCode) {
        Log.d(TAG, "dressCodeSet: dressCode = " + selectedDressCode);
        Map<String, Object> dataD = new HashMap<>();
        dataD.put("dressCode", selectedDressCode);
        db.collection(pheresCollection).document(selectedPhere.getPhereName()).set(dataD, SetOptions.merge());
    }


}