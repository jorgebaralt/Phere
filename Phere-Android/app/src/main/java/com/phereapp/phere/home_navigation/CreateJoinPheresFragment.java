package com.phereapp.phere.home_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.phereapp.phere.R;
import com.phereapp.phere.adapters.PhereTabAdapter;
import com.phereapp.phere.phere_handling.CreateNewPhereActivity;
import com.phereapp.phere.phere_handling.JoinPhereByNameActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateJoinPheresFragment extends Fragment {
    FloatingActionButton mCreatePhere;
    FloatingActionButton mJoinPhere;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_create_join_pheres, container, false);

        //FAB
        mCreatePhere = rootView.findViewById(R.id.fab_createPhere);
        mJoinPhere = rootView.findViewById(R.id.fab_joinPhere);

        mCreatePhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createNewPhereIntent = new Intent(getActivity(), CreateNewPhereActivity.class);
                startActivity(createNewPhereIntent);
                //TODO : CREATE INTENT AND TRANSITION FROM FRAGMENT TO NEW ACTIVY

            }
        });
        mJoinPhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinPhereIntent = new Intent(getActivity(), JoinPhereByNameActivity.class);
                startActivity(joinPhereIntent);

            }
        });



        //Tab and viewpager
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_createJoinPhere);
        PhereTabAdapter phereTabAdapter = new PhereTabAdapter(getChildFragmentManager());
        viewPager.setAdapter(phereTabAdapter);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_createJoinPhere);
        tabLayout.setupWithViewPager(viewPager);




        return rootView;
    }

}
