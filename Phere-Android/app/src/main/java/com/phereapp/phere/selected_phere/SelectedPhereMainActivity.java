package com.phereapp.phere.selected_phere;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";
    CollapsingToolbarLayout mPhereImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_main);

        Phere selectedPhere = (Phere) SelectedPhereMainActivity.this.getIntent().getSerializableExtra("SelectedPhere");
        if(selectedPhere != null){
            Log.d(TAG, "onCreate: WE GOT THE PHERE ************" + selectedPhere.getPhereName());
        }

        mPhereImage = findViewById(R.id.toolbar_selectedPhere);
        mPhereImage.setTitle(selectedPhere.getPhereName());
        //mPhereImage.setBackground();
        assert selectedPhere != null;

        setTitle(selectedPhere.getPhereName());


    }
}
