package com.phereapp.phere.phere_handling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phereapp.phere.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedPheresFragment extends Fragment {


    public JoinedPheresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joined_pheres, container, false);
    }

}
