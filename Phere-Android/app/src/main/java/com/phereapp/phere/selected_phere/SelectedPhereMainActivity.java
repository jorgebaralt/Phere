package com.phereapp.phere.selected_phere;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phereapp.phere.R;
import com.phereapp.phere.dialog_fragments.MembersDialogFragment;
import com.phereapp.phere.dynamic_image_view.DynamicImageView;
import com.phereapp.phere.pojo.Phere;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";
    private TextView mPhereDescription, mPhereDate, mPhereLocation, mPhereMembers, mPherePlaylist, mPhereSingleLineDesc;
    private TextView mPhereLocationPopup;
    private DynamicImageView mPhereProfilePicture;
    private ImageButton mToggleDescription;
    CollapsingToolbarLayout mTitle;
    private String mPhereImageUrl, mHost, mCurrentUser;
    private android.support.v7.widget.Toolbar mToolbar;
    private Phere selectedPhere;
    private Calendar myCalendar = Calendar.getInstance();
    private Date mDate;
    //Firebase
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_main);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        selectedPhere = (Phere) SelectedPhereMainActivity.this.getIntent().getSerializableExtra("SelectedPhere");
        if(selectedPhere == null){
            Log.e(TAG, "onCreate: Error passing phere to this activity");
        }
        //Initialize the Views
        mPhereDescription = findViewById(R.id.txt_phere_description_selectedPhere);
        mPhereSingleLineDesc = findViewById(R.id.txt_singleLine_description_selectedPhere);
        mTitle = findViewById(R.id.toolbar_selectedPhere);
        mToolbar = findViewById(R.id.toolbarid);
        mPhereProfilePicture = findViewById(R.id.img_profilePicture_selectedPhere);
        mPhereDate = findViewById(R.id.txt_btn_date_selectedPhere);
        mPhereLocation = findViewById(R.id.txt_btn_location_selectedPhere);
        mPhereMembers = findViewById(R.id.txt_btn_members_selectedPhere);
        mPherePlaylist = findViewById(R.id.txt_btn_playlist_selectedPhere);
        mToggleDescription = findViewById(R.id.btn_toggleDescription_selectedPhere);

        mTitle.setTitle(selectedPhere.getDisplayPhereName());

        //Custom Dialog for members
        final FragmentManager fm = getFragmentManager();
        final MembersDialogFragment mdf = new MembersDialogFragment();

        mPhereSingleLineDesc.setText(selectedPhere.getPhereDescription());
        mPhereDescription.setText(selectedPhere.getPhereDescription());

        mPhereImageUrl = selectedPhere.getImageURL();
        Glide.with(this).load(mPhereImageUrl).centerCrop().into(mPhereProfilePicture);

        mToggleDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescription();

            }
        });

        //Menu for the toolbar
        mHost = selectedPhere.getHost();
        mCurrentUser = mUser.getDisplayName();
        if (mHost.equals(mCurrentUser)) {
            setSupportActionBar(mToolbar);
            mToolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cogwheel_48dp_brighter));
        }

        mPherePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playlistIntent = new Intent(SelectedPhereMainActivity.this, SelectedPherePlaylistActivity.class);
                startActivity(playlistIntent);
            }
        });

        mPhereMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("SelectedPhere", selectedPhere);
                mdf.setArguments(bundle);
                mdf.show(fm, "Members_tag");
            }
        });

        mPhereLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertBox();
            }
        });

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            mDate = df.parse(selectedPhere.getPhereDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myCalendar.setTime(mDate);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };


        mPhereDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpdialog = new DatePickerDialog (SelectedPhereMainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dpdialog.show();
                dpdialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                dpdialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
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
        switch (item.getItemId()) {

            case R.id.settings_selected_phere:
                Intent settingsIntent = new Intent(SelectedPhereMainActivity.this, SelectedPhereSettingsActivity.class);
                settingsIntent.putExtra("SelectedPhere",  selectedPhere);
                startActivity(settingsIntent);
                break;
        }
        return true;
    }

    //Show description of specific phere.
    private void showDescription() {
        //Getting the current visibility of the TextView
        int visibility = mPhereDescription.getVisibility();
        //Getting the LayoutParameters to change the Height
        ViewGroup.LayoutParams params = mPhereDescription.getLayoutParams();
        ViewGroup.LayoutParams singleLineParams = mPhereSingleLineDesc.getLayoutParams();
        if (visibility == View.INVISIBLE) {
            //Changes the arrow drawable to be UP
            mToggleDescription.setImageResource(R.drawable.arrow_up_float);
            //Setting the Height of the TextView to Wrap_Content and showing the text
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mPhereDescription.setLayoutParams(params);
            mPhereDescription.setVisibility(View.VISIBLE);
            singleLineParams.height = 0;
            mPhereSingleLineDesc.setLayoutParams(singleLineParams);
            mPhereSingleLineDesc.setVisibility(View.INVISIBLE);
        } else {
            //Changes the arrow drawable to be DOWN
            mToggleDescription.setImageResource(R.drawable.arrow_down_float);
            //Setting the Height of the TextView to 0dp and hiding the text
            params.height = 0;
            mPhereDescription.setLayoutParams(params);
            mPhereDescription.setVisibility(View.INVISIBLE);
            singleLineParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mPhereSingleLineDesc.setLayoutParams(singleLineParams);
            mPhereSingleLineDesc.setVisibility(View.VISIBLE);

        }
    }

    public void showAlertBox() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Phere Location");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_location_dialog);
        mPhereLocationPopup = dialog.findViewById(R.id.txt_location_popUp);
        mPhereLocationPopup.setText(selectedPhere.getPhereLocation());
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
    }

}
