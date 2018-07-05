package com.phereapp.phere.dialog_fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.phereapp.phere.R;

public class DressCodeDialogFragment extends DialogFragment {
    public TextView mFormal, mSemiFormal, mCasual;
    public DressCodeSet mCallback;

    // Interface for when the user selects an option
    public interface DressCodeSet {
        void dressCodeSet (String selectedDressCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dress_code_dialog_fragment, container);

        mFormal = rootView.findViewById(R.id.txt_dressCode_formal_selectedPhere_settings);
        mSemiFormal = rootView.findViewById(R.id.txt_dressCode_semiFormal_selectedPhere_settings);
        mCasual = rootView.findViewById(R.id.txt_dressCode_casual_selectedPhere_settings);


        // Handling events for user selections and Toasts of selected option.
        mFormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dressCodeF = "Formal";
                mCallback.dressCodeSet(dressCodeF);
                Toast.makeText(getActivity(), dressCodeF, Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });

        mSemiFormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dressCodeSF = "SemiFormal";
                mCallback.dressCodeSet(dressCodeSF);
                Toast.makeText(getActivity(), dressCodeSF, Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });

        mCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dressCodeC = "Casual";
                mCallback.dressCodeSet(dressCodeC);
                Toast.makeText(getActivity(), dressCodeC, Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });

        this.getDialog().setCanceledOnTouchOutside(true);

        return rootView;
    }

    // Retrieving the User selection
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (DressCodeSet) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Most implement DressCodeSet");
        }
    }
}
