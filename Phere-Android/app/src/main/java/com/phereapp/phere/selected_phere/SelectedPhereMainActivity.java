package com.phereapp.phere.selected_phere;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.dynamic_image_view.DynamicImageView;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";
    private TextView mPhereDescription;
    private DynamicImageView mPhereProfilePicture;
    CollapsingToolbarLayout mTitle;
    private String mPhereImageUrl;

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
        mPhereProfilePicture = (DynamicImageView) findViewById(R.id.img_profilePicture_selectedPhere);
        mPhereImageUrl = selectedPhere.getImageURL();


        //Sets the profile image from the Download URL
        Glide.with(this).load(mPhereImageUrl).centerCrop().into(mPhereProfilePicture);



    }
}
