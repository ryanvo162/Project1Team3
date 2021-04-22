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
import com.ps14483.project1_team3.adapter.Guest_TabLayoutSPAdapter;

public class Guest_SanPhamFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    ImageView imgToolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tablayout_sp, container, false);
        tabLayout=view.findViewById(R.id.tab_sp);
        viewPager=view.findViewById(R.id.vpg_sp);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        imgToolbar = (ImageView) toolbar.findViewById(R.id.imgToolbar);
        imgToolbar.setImageResource(R.drawable.toolbar_sp);
        Guest_TabLayoutSPAdapter adapter=new Guest_TabLayoutSPAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
