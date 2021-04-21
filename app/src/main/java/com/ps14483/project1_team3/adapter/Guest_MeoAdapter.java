package com.ps14483.project1_team3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.model.Meo;
import com.squareup.picasso.Picasso;

public class Guest_MeoAdapter extends FirebaseRecyclerAdapter<Meo, Guest_MeoAdapter.Holder> {
    private Context context;
    public String[] mColors = {"#F5D0C1","#C6D8D4"};

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Guest_MeoAdapter(@NonNull FirebaseRecyclerOptions<Meo> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Meo meo) {
        holder.ten.setText(meo.ten+"");
        holder.gia.setText(meo.gia+"");
        holder.maulong.setText(meo.maulong+"");
        holder.tvchitiet.setText(meo.chitiet);
        String imgUri= meo.img+"";
        Picasso.get().load(imgUri).into(holder.imguri);
        holder.linearLayout.setBackgroundColor(Color.parseColor(mColors[position % 2]));
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_item_cho_meo,parent,false);
        return new Holder(view);


    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView ten,gia,maulong,tvchitiet;
        ImageView  imguri;
        CardView card;
        LinearLayout linearLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.guest_item_cho_meo);
            ten=itemView.findViewById(R.id.tv_guest_tenpet);
            gia=itemView.findViewById(R.id.tv_guest_giapet);
            maulong=itemView.findViewById(R.id.tv_guest_maulong);
            imguri =itemView.findViewById(R.id.img_guest_pet);
            tvchitiet=itemView.findViewById(R.id.tv_guest_chitietpet);
            linearLayout=itemView.findViewById(R.id.linear_guest_cho_meo);
        }
    }
}
