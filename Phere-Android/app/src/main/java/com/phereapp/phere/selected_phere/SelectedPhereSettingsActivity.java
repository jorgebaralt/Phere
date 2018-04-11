package com.phereapp.phere.selected_phere;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

import net.steamcrafted.lineartimepicker.dialog.LinearTimePickerDialog;

import java.util.HashMap;
import java.util.Map;

public class SelectedPhereSettingsActivity extends AppCompatActivity {
    private static final String TAG = "SelectedPhereSettings";
    private Phere selectedPhere;
    private android.support.v7.widget.Toolbar mToolbar;
    private Button mDeletePhere, mSetTime;
    private String phereName, imageURL;
    private String mHour, mMinutes, mFullTime;
    //Firebase
    private FirebaseFirestore db;
    private StorageReference mStorageReference;
    private DatabaseReference rootRef;
    private String pheresCollection = "pheres";
    private String phereTime = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_settings);

         rootRef = FirebaseDatabase.getInstance().getReference();
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
                LinearTimePickerDialog dialog = LinearTimePickerDialog.Builder.with(SelectedPhereSettingsActivity.this)
                        .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                String daytime = "";
                                if (hour > 12) {
                                    hour = hour - 12;
                                    daytime = "PM";
                                    mHour = Integer.toString(hour);
                                } else {
                                    daytime = "AM";
                                    mHour = Integer.toString(hour);
                                }
                                mMinutes = Integer.toString(minutes);
                                if (mMinutes.equals("0")) {
                                    mMinutes = "00";
                                }
                                Toast.makeText(SelectedPhereSettingsActivity.this, hour + ":" + mMinutes + " " + daytime, Toast.LENGTH_SHORT).show();
                                mFullTime = (hour + ":" + mMinutes + " " + daytime);
                                Map<String, Object> data = new HashMap<>();
                                data.put("time", mFullTime);
                                db.collection(pheresCollection).document(selectedPhere.getPhereName()).set(data, SetOptions.merge());
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        }).setPickerBackgroundColor(getResources().getColor(R.color.black))
                        .setTextColor(getResources().getColor(R.color.lightBlue))
                        .setButtonColor(getResources().getColor(R.color.lightBlue))
                        .build();
                dialog.show();

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