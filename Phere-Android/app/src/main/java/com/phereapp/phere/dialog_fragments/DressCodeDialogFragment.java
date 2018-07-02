package com.phereapp.phere.dialog_fragments;

import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phereapp.phere.R;

public class DressCodeDialogFragment extends DialogFragment {
    public TextView mFormal, mSemiFormal, mCasual;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dress_code_dialog_fragment, container);

        mFormal = rootView.findViewById(R.id.txt_dressCode_formal_selectedPhere_settings);
        mSemiFormal = rootView.findViewById(R.id.txt_dressCode_semiFormal_selectedPhere_settings);
        mCasual = rootView.findViewById(R.id.txt_dressCode_casual_selectedPhere_settings);

        mFormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        this.getDialog().setCanceledOnTouchOutside(true);
        this.getDialog().setTitle("Dress Code");

        return rootView;
    }
}
