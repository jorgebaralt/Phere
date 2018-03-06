package com.phereapp.phere.selected_phere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.phereapp.phere.R;
import com.phereapp.phere.pojo.Phere;

public class SelectedPhereMainActivity extends AppCompatActivity {

    private static String TAG = "SelectedPhereMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_phere_main);

        Phere selectedPhere = (Phere) SelectedPhereMainActivity.this.getIntent().getSerializableExtra("SelectedPhere");
        if(selectedPhere != null){
            Log.d(TAG, "onCreate: WE GOT THE PHERE ************" + selectedPhere.getPhereName());
        }
        TextView title = findViewById(R.id.txt_title_selectedPhere);

        assert selectedPhere != null;
        title.setText(selectedPhere.getPhereName());

    }
}
