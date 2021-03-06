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
import com.ps14483.project1_team3.model.ThucAn;
import com.squareup.picasso.Picasso;

public class Guest_ThucAnAdapter extends FirebaseRecyclerAdapter<ThucAn, Guest_ThucAnAdapter.Holder> {
    private Context context;
    public String[] mColors = {"#C6D8D4","#F5D0C1"};

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Guest_ThucAnAdapter(@NonNull FirebaseRecyclerOptions<ThucAn> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull ThucAn thucAn) {
        holder.ten.setText(thucAn.ten+"");
        holder.gia.setText(thucAn.gia+"");
        holder.tvchitiet.setText(thucAn.chitiet);
        String imgUri= thucAn.img+"";
        Picasso.get().load(imgUri).into(holder.imguri);
        holder.linearLayout.setBackgroundColor(Color.parseColor(mColors[position % 2]));
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_sp,parent,false);
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
            card=itemView.findViewById(R.id.guest_item_sp);
            ten=itemView.findViewById(R.id.tv_guest_tensp);
            gia=itemView.findViewById(R.id.tv_guest_giapsp);
            imguri =itemView.findViewById(R.id.img_guest_sp);
            tvchitiet=itemView.findViewById(R.id.tv_guest_chitietsp);
            linearLayout=itemView.findViewById(R.id.linear_guest_sp);
        }
    }
}
