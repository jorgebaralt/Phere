package com.phereapp.phere.phere_handling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.phereapp.phere.MainActivityUser;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoreInfoCreatePhereActivity extends AppCompatActivity {
    private Button mUploadFromGallery, mUploadFromCamera, mUploadFromOurGallery;
    private Button btnOk, btnCancel;
    private ImageView mUploadedProfilePic;
    private EditText mPhereDescription;
    private final int REQUEST_CODE_EXTERNAL_IMAGE = 2000;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static String TAG = "MoreInfoCreatePhereActivity";
    private Uri filePath;
    private Phere newPhere;
    private String phereDescription, mCurrentPhotoPath;
    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private String pheresCollection = "pheres";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_create_phere);

        newPhere = (Phere) MoreInfoCreatePhereActivity.this.getIntent().getSerializableExtra("NewPhere");

        //Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Firebase Database
        db = FirebaseFirestore.getInstance();

        //Initializes the Views
        mUploadedProfilePic = (ImageView) findViewById(R.id.img_uploaded_fromGallery);
        mUploadFromGallery = (Button) findViewById(R.id.btn_upload_imgFromGallery);
        mUploadFromCamera = (Button) findViewById(R.id.btn_upload_imgFromCamera);
        mUploadFromOurGallery = (Button) findViewById(R.id.btn_upload_imgFromOurGallery);
        btnOk = (Button) findViewById(R.id.btn_ok_moreInfoPhere);
        btnCancel = (Button) findViewById(R.id.btn_cancel_moreInfoPhere);
        mPhereDescription = (EditText) findViewById(R.id.editTxt_phere_descriptionInput);

        // On click of the upload picture Button
        mUploadFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose picture from Library (sends the user to their gallery)
                Intent picFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picFromGallery, REQUEST_CODE_EXTERNAL_IMAGE);

            }
        });

        mUploadFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                addPhereReference();
                Intent mainActivityIntent = new Intent(MoreInfoCreatePhereActivity.this, MainActivityUser.class);
                startActivity(mainActivityIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent(MoreInfoCreatePhereActivity.this, CreateNewPhereActivity.class);
                startActivity(goBackIntent);
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
                mUploadedProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: ImageURI = " + filePath);
            mUploadedProfilePic.setImageURI(filePath);
        }


    }

    private void addPhereReference() {
        Log.d(TAG, "addUserReference: Creating Phere" + newPhere.getPhereName() + " for = " + newPhere.getHost());
        // Getting the phere description from the user
        phereDescription = mPhereDescription.getText().toString();
        newPhere.setPhereDescription(phereDescription);

        // adds the extra information to the document in the database
        db.collection(pheresCollection).document(newPhere.getPhereName()).set(newPhere).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Phere Created");
                Toast.makeText(MoreInfoCreatePhereActivity.this, "New Phere Created", Toast.LENGTH_SHORT).show();
                //go back to main intent.
                //TODO: Take host to main Phere Activity so they can modify its description, Picture, Etc...


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: Error creating Phere...");
            }
        });
    }


    private void UploadImage() {
        Log.d(TAG, "UploadImage: Initializing UploadImage");
        if (filePath != null) {
            Log.d(TAG, "UploadImage: Image from gallery is being Uploaded");
            // New instance of ProgressDialog being initialized
            final ProgressDialog progressDialog = new ProgressDialog(this);
            // Shows the user that the image is being Uploaded
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Creates the reference in the fireface Storage to be able to access the uploaded image
            StorageReference ref = storageReference.child("phereProfileImage/" + newPhere.getPhereName() + "ProfileImage");
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Shows a message to the user if the image got Uploaded
                    progressDialog.dismiss();
                    Toast.makeText(MoreInfoCreatePhereActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Shows a message to the user if the image did not get Uploaded
                    progressDialog.dismiss();
                    Toast.makeText(MoreInfoCreatePhereActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // Shows a percentage of the upload Progress for the user to see.
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }

    }

    private File createImageFile() throws IOException {
        // Create an Image File name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent picFromCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (picFromCamera.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the file
            }
            // Continuing with the file created
            if (photoFile != null) {
                filePath = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                picFromCamera.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
                startActivityForResult(picFromCamera, CAMERA_REQUEST_CODE);
            }
        }
    }
}