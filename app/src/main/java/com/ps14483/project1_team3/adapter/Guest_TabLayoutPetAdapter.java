package com.ps14483.project1_team3.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ps14483.project1_team3.fragment.Guest_ChoFragment;
import com.ps14483.project1_team3.fragment.Guest_MeoFragment;


public class Guest_TabLayoutPetAdapter extends FragmentStatePagerAdapter {
    public Guest_TabLayoutPetAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new Guest_ChoFragment();
            case 1:return new Guest_MeoFragment();
            default:return new Guest_ChoFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position)
        {
            case 0: title="Chó";
                break;
            case 1: title="Mèo";
                break;
        }
        return title;

    }

    @Override
    public int getCount() {
        return 2;
    }
}
