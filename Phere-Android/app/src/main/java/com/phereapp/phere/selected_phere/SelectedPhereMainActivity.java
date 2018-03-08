package com.phereapp.phere.selected_phere;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";
    private TextView mPhereDescription;
    private ImageView mPhereProfilePicture;
    CollapsingToolbarLayout mPhereImage;
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

        //Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Initialize the Views
        mPhereDescription = (TextView) findViewById(R.id.txt_phere_description_selectedPhere);
        mPhereDescription.setText(selectedPhere.getPhereDescription());
        mPhereImage = findViewById(R.id.toolbar_selectedPhere);
        mPhereImage.setTitle(selectedPhere.getPhereName());
        mPhereProfilePicture = (ImageView) findViewById(R.id.img_phere_profilePicture_selectedPhere);

        //mPhereImage.setBackground();
        assert selectedPhere != null;

        setTitle(selectedPhere.getPhereName());


    }
}
