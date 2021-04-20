package com.ps14483.project1_team3.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ps14483.project1_team3.fragment.DoChoiFragment;
import com.ps14483.project1_team3.fragment.ThucAnFragment;


public class TabLayoutSPAdapter extends FragmentStatePagerAdapter {


    public TabLayoutSPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new ThucAnFragment();
            case 1:return new DoChoiFragment();
            default:return new ThucAnFragment();
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
