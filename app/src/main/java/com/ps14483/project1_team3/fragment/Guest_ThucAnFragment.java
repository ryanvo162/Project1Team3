package com.ps14483.project1_team3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.adapter.Guest_ThucAnAdapter;
import com.ps14483.project1_team3.model.ThucAn;

import java.util.ArrayList;

public class Guest_ThucAnFragment extends Fragment {
    RecyclerView rcv;
    Guest_ThucAnAdapter adapter;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("PetShop");
    DatabaseReference spref=reference.child("sanpham");
    StorageReference storage= FirebaseStorage.getInstance().getReference();
    FloatingActionButton flb;


    ArrayList<String> list=new ArrayList<String>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_guest_thucan, container, false);
        rcv=view.findViewById(R.id.rc_thucan);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<ThucAn> options=new FirebaseRecyclerOptions.Builder<ThucAn>()
                .setQuery(spref.child("ThucAn"),ThucAn.class).build();
        adapter=new Guest_ThucAnAdapter(options,getContext());
        rcv.setAdapter(adapter);
        flb=view.findViewById(R.id.flb_thucan);
        flb.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
