package com.phereapp.phere.selected_phere;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.phereapp.phere.R;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private Button uploadFromGallery;
    private ImageView uploadedProfilePic;
    private final int REQUEST_CODE_EXTERNAL_IMAGE = 2000;
    private String TAG = "SelectedPhereActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_selected_phere_main);

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
