package com.ps14483.project1_team3.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ps14483.project1_team3.fragment.Guest_DoChoiFragment;
import com.ps14483.project1_team3.fragment.Guest_ThucAnFragment;


public class Guest_TabLayoutSPAdapter extends FragmentStatePagerAdapter {


    public Guest_TabLayoutSPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new Guest_ThucAnFragment();
            case 1:return new Guest_DoChoiFragment();
            default:return new Guest_ThucAnFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position)
        {
            case 0: title="Thức Ăn";
                break;
            case 1: title="Đồ Chơi";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
