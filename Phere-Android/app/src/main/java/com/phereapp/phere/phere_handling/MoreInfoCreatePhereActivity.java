package com.phereapp.phere.phere_handling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

import java.io.IOException;
import java.util.UUID;

public class MoreInfoCreatePhereActivity extends AppCompatActivity {
    private Button uploadFromGallery;
    private ImageView uploadedProfilePic;
    private final int REQUEST_CODE_EXTERNAL_IMAGE = 2000;
    private static String TAG = "MoreInfoCreatePhereActivity";
    private Uri filePath;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    private Phere selectedPhere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_create_phere);

        //Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        selectedPhere = (Phere) MoreInfoCreatePhereActivity.this.getIntent().getSerializableExtra("SelectedPhere");

        //Initializes the Views
        uploadedProfilePic = (ImageView) findViewById(R.id.img_uploaded_fromGallery);
        uploadFromGallery = (Button) findViewById(R.id.btn_upload_imgFromGallery);

        // On click of the upload picture Button
        uploadFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose picture from Library (sends the user to their gallery)
                Intent picFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picFromGallery, REQUEST_CODE_EXTERNAL_IMAGE);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Checking if the image got selected OK
        if (requestCode == REQUEST_CODE_EXTERNAL_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            // Getting the Data in URI format to resolve the bitmap
            filePath = data.getData();
            try {
                // Mapping the bitmap from its domain.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadedProfilePic.setImageBitmap(bitmap);
                uploadImage();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            // New instance of ProgressDialog being initialized
            final ProgressDialog progressDialog = new ProgressDialog(this);
            // Shows the user that the image is being Uploaded
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Creates the reference in the fireface Storage to be able to access the uploaded image
            StorageReference ref = storageReference.child("phereProfileImage/" + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Shows a message to the user if the image got Uploaded
                    progressDialog.dismiss();
                    Toast.makeText(MoreInfoCreatePhereActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }) .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Shows a message to the user if the image did not get Uploaded
                    progressDialog.dismiss();
                    Toast.makeText(MoreInfoCreatePhereActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // Shows a percentage of the upload Progress for the user to see.
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                }
            });
        }
    }
}