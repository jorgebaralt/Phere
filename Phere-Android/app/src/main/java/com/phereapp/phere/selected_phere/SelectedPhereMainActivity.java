package com.phereapp.phere.selected_phere;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.phereapp.phere.R;
import com.phereapp.phere.dynamic_image_view.DynamicImageView;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";
    private TextView mPhereDescription, mPhereDate, mPhereLocation, mPhereMembers, mPherePlaylist;
    private DynamicImageView mPhereProfilePicture;
    private ImageButton mToggleDescription;
    CollapsingToolbarLayout mTitle;
    private String mPhereImageUrl;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_main);

        Phere selectedPhere = (Phere) SelectedPhereMainActivity.this.getIntent().getSerializableExtra("SelectedPhere");
        if(selectedPhere != null){
            Log.d(TAG, "onCreate: WE GOT THE PHERE ************" + selectedPhere.getPhereName());
        }
        //Initialize the Views
        mPhereDescription = findViewById(R.id.txt_phere_description_selectedPhere);
        mTitle = findViewById(R.id.toolbar_selectedPhere);
        mToolbar = findViewById(R.id.toolbarid);
        mPhereProfilePicture = findViewById(R.id.img_profilePicture_selectedPhere);
        mPhereDate = findViewById(R.id.txt_btn_date_selectedPhere);
        mPhereLocation = findViewById(R.id.txt_btn_location_selectedPhere);
        mPhereMembers = findViewById(R.id.txt_btn_members_selectedPhere);
        mPherePlaylist = findViewById(R.id.txt_btn_playlist_selectedPhere);
        mToggleDescription = findViewById(R.id.btn_toggleDescription_selectedPhere);

        //Sets the title of the Phere
        mTitle.setTitle(selectedPhere.getDisplayPhereName());
        //Sets the description of the Phere
        mPhereDescription.setText(selectedPhere.getPhereDescription());
        //Sets the profile image from the Download URL
        mPhereImageUrl = selectedPhere.getImageURL();
        Glide.with(this).load(mPhereImageUrl).centerCrop().into(mPhereProfilePicture);
        //Sets the date of the Phere
        mPhereDate.setText(selectedPhere.getPhereDate());
        //Makes the description text appear
        mToggleDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescription();
            }
        });
        //Menu for the toolbar
        setSupportActionBar(mToolbar);

        mPherePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playlistIntent = new Intent(SelectedPhereMainActivity.this, SelectedPherePlaylistActivity.class);
                startActivity(playlistIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected_phere, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Show description of specific phere.
    public void showDescription() {
        //Getting the current visibility of the TextView
        int visibility = mPhereDescription.getVisibility();
        //Getting the LayoutParameters to change the Height
        ViewGroup.LayoutParams params = mPhereDescription.getLayoutParams();
        if (visibility == View.INVISIBLE) {
            //Changes the arrow drawable to be UP
            mToggleDescription.setImageResource(R.drawable.arrow_up_float);
            //Setting the Height of the TextView to Wrap_Content and showing the text
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mPhereDescription.setLayoutParams(params);
            mPhereDescription.setVisibility(View.VISIBLE);
        } else {
            //Changes the arrow drawable to be DOWN
            mToggleDescription.setImageResource(R.drawable.arrow_down_float);
            //Setting the Height of the TextView to 0dp and hiding the text
            params.height = 0;
            mPhereDescription.setLayoutParams(params);
            mPhereDescription.setVisibility(View.INVISIBLE);
        }
    }
}
