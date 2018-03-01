package com.phereapp.phere.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.phereapp.phere.phere_handling.HostingPheresFragment;
import com.phereapp.phere.phere_handling.JoinedPheresFragment;

/**
 * Created by User on 2/27/2018.
 */

public class PhereTabAdapter extends FragmentStatePagerAdapter {
    public PhereTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HostingPheresFragment();
            default:
                return new JoinedPheresFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "HOSTING PHERES";
            default:
                return "JOINED PHERES";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
