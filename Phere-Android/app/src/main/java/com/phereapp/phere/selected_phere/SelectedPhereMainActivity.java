package com.phereapp.phere.selected_phere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {
    private Button uploadFromGallery;
    private ImageView uploadedProfilePic;
    private final int REQUEST_CODE_EXTERNAL_IMAGE = 2000;
    private static String TAG = "SelectedPhereMainActivity";

    private Phere selectedPhere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_main);


        selectedPhere = (Phere) SelectedPhereMainActivity.this.getIntent().getSerializableExtra("SelectedPhere");

        uploadedProfilePic = (ImageView) findViewById(R.id.img_uploaded_fromGallery);
        uploadFromGallery = (Button) findViewById(R.id.btn_upload_imgFromGallery);
        uploadFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose picture from Library
                Intent picFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(picFromGallery, REQUEST_CODE_EXTERNAL_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_EXTERNAL_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    uploadedProfilePic.setImageURI(data.getData());
                }
                break;
            default:
                break;
        }
    }
}