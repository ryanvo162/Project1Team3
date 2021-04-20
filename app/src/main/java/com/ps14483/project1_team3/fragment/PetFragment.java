package com.ps14483.project1_team3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.adapter.TabLayoutPetAdapter;

public class PetFragment extends Fragment {
    Toolbar toolbar;
    ImageView imgToolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tablayout_pet, container, false);
        tabLayout=view.findViewById(R.id.tab_pet);
        viewPager=view.findViewById(R.id.vpg_pet);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        imgToolbar = (ImageView) toolbar.findViewById(R.id.imgToolbar);
        imgToolbar.setImageResource(R.drawable.toolbar_pets);
        TabLayoutPetAdapter adapter=new TabLayoutPetAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
