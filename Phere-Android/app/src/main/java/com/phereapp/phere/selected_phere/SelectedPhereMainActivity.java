package com.phereapp.phere.selected_phere;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";
    private TextView mPhereDescription;
    private ImageView mPhereProfilePicture;
    CollapsingToolbarLayout mTitle;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_main);

        Phere selectedPhere = (Phere) SelectedPhereMainActivity.this.getIntent().getSerializableExtra("SelectedPhere");
        if(selectedPhere != null){
            Log.d(TAG, "onCreate: WE GOT THE PHERE ************" + selectedPhere.getPhereName());
        }
        //Initialize the Views
        mPhereDescription = (TextView) findViewById(R.id.txt_phere_description_selectedPhere);
        mPhereDescription.setText(selectedPhere.getPhereDescription());
        mTitle = findViewById(R.id.toolbar_selectedPhere);
        mTitle.setTitle(selectedPhere.getDisplayPhereName());
        mPhereProfilePicture = (ImageView) findViewById(R.id.img_profilePicture_selectedPhere);


        //Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference = storageReference.child("phereProfileImage/" + selectedPhere.getPhereName() + "_profileImage");
        //get profile image from DB and put into image
        Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(mPhereProfilePicture);



    }
}
