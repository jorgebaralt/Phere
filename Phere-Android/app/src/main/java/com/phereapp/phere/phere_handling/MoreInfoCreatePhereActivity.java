package com.phereapp.phere.phere_handling;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
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
import com.phereapp.phere.pojo.PherePlaylist;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MoreInfoCreatePhereActivity extends AppCompatActivity {
    private Button mUploadFromGallery, mUploadFromCamera;
    private Button btnOk, btnCancel;
    private ImageView mUploadedProfilePic;
    private EditText mPhereDescription, mPhereDate;
    private final int REQUEST_CODE_EXTERNAL_IMAGE = 2000;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri filePath, imageURL;
    private Phere newPhere;
    private String phereDescription, mCurrentPhotoPath;
    private String imagePath, phereDate, uniqueId, phereImageUrl;
    private Calendar myCalendar = Calendar.getInstance();
    private long time = myCalendar.getTimeInMillis();
    final Calendar currentDate = Calendar.getInstance();
    private Context mContext;
    private PherePlaylist pherePlaylist;

    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private String pheresCollection = "pheres";
    private StorageReference ref;

    private static final String TAG = "MoreInfoCreatePhereActi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_create_phere);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        newPhere = (Phere) MoreInfoCreatePhereActivity.this.getIntent().getSerializableExtra("NewPhere");
        pherePlaylist = (PherePlaylist) MoreInfoCreatePhereActivity.this.getIntent().getSerializableExtra("pherePlaylist");
        assert newPhere != null;
        assert pherePlaylist != null;

        //Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Firebase Database
        db = FirebaseFirestore.getInstance();

        //Initializes the Views
        mPhereDate = (EditText) findViewById(R.id.editTxt_pickDate_MoreInfo);
        mUploadedProfilePic = (ImageView) findViewById(R.id.img_uploaded_fromGallery);
        mUploadFromGallery = (Button) findViewById(R.id.btn_upload_imgFromGallery);
        mUploadFromCamera = (Button) findViewById(R.id.btn_upload_imgFromCamera);
        btnOk = (Button) findViewById(R.id.btn_ok_moreInfoPhere);
        btnCancel = (Button) findViewById(R.id.btn_cancel_moreInfoPhere);
        mPhereDescription = (EditText) findViewById(R.id.editTxt_phere_descriptionInput);


        // On click of the upload picture Button
        mUploadFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Choose picture from Library (sends the user to their gallery)
                Intent picFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picFromGallery, REQUEST_CODE_EXTERNAL_IMAGE);
            }
        });

        //On click to take picture with camera
        mUploadFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

         //Listener for when the user picks the date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        // On Click of the pick a date editText
        mPhereDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpdialog = new DatePickerDialog (MoreInfoCreatePhereActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dpdialog.show();
                dpdialog.getDatePicker().setMinDate(time);
            }
        });

        //continue button
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();

            }
        });

        //go back to previous activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent(MoreInfoCreatePhereActivity.this, CreateNewPhereActivity.class);
                startActivity(goBackIntent);
            }
        });
    }

    //handles result of whether opening camera or taking picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Checking if the image got selected OK
        if (requestCode == REQUEST_CODE_EXTERNAL_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            // Getting the Data in URI format to resolve the bitmap
            filePath = data.getData();
            mUploadedProfilePic.setImageURI(filePath);

        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: ImageURI = " + filePath);
            mUploadedProfilePic.setImageURI(filePath);
        }
    }


    //Saves image into FIREBASE Storage
    private void UploadImage() {
        uniqueId = UUID.randomUUID().toString();
        Log.d(TAG, "UploadImage: Initializing UploadImage");
        if (filePath != null) {
            Log.d(TAG, "UploadImage: Image from gallery is being Uploaded");
            // New instance of ProgressDialog being initialized
            final ProgressDialog progressDialog = new ProgressDialog(this);
            // Shows the user that the image is being Uploaded
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Creates the reference in the firebase Storage to be able to access the uploaded image
            imagePath = "phereProfileImage/" + uniqueId + "_" + newPhere.getPhereName();
            ref = storageReference.child(imagePath);
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Shows a message to the user if the image got Uploaded
                    progressDialog.dismiss();
                    imageURL = taskSnapshot.getDownloadUrl();
                    addPhereReference();
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
        } else {
            addPhereReference();
        }
    }

    //creates the new Phere and adds it to the database.
    private void addPhereReference() {
        Log.d(TAG, "addUserReference: Creating Phere" + newPhere.getPhereName() + " for = " + newPhere.getHost());
        // Getting the phere info from the user
        phereDescription = mPhereDescription.getText().toString();
        phereDate = mPhereDate.getText().toString();
        newPhere.setPhereDescription(phereDescription);
        newPhere.setPhereDate(phereDate);
        //todo : add phereplaylist to pojo
        newPhere.setPherePlaylist(pherePlaylist);
        if (filePath != null) {
            phereImageUrl = imageURL.toString();
            newPhere.setImageURL(phereImageUrl);
        }

        // adds the extra information to the document in the database
        db.collection(pheresCollection).document(newPhere.getPhereName()).set(newPhere).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Phere Created");
                Toast.makeText(MoreInfoCreatePhereActivity.this, "New Phere Created", Toast.LENGTH_SHORT).show();
                //move to next intent and destroy
                Intent mainActivityIntent = new Intent(MoreInfoCreatePhereActivity.this, MainActivityUser.class);
                startActivity(mainActivityIntent);
                CreateNewPhereActivity.mCreateNewPhereActivity.finish();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: Error creating Phere...");
            }
        });
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

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);

        mPhereDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}