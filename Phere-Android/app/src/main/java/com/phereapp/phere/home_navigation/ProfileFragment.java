package com.phereapp.phere.home_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.phereapp.phere.R;
import com.phereapp.phere.login.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button logoutBtn;
    private String TAG = "ProfileFragment";
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn = rootView.findViewById(R.id.btn_logout_profile);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: logging out...");
                FirebaseAuth.getInstance().signOut();
                //handles Facebook logout
                LoginManager.getInstance().logOut();
                //move to start intent for logins options
                Intent startIntent = new Intent(getActivity(),StartActivity.class);
                startActivity(startIntent);
                getActivity().finish();
            }
        });



        return rootView;
    }

}
