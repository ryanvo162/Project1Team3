package com.ps14483.project1_team3.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.model.Hoadon;

import java.util.ArrayList;
import java.util.HashMap;

public class HoaDonAdapter extends FirebaseRecyclerAdapter<Hoadon,HoaDonAdapter.Holder> {


    private Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HoaDonAdapter(@NonNull FirebaseRecyclerOptions<Hoadon> options, Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Hoadon hoadon) {
        holder.tvloai.setText(hoadon.loaisp+"");
        holder.tvtensp.setText(hoadon.tensp+"");
        holder.tvtenkh.setText(hoadon.tenkh+"");
        holder.tvgia.setText(hoadon.giamua+"");
        holder.tvngay.setText(hoadon.ngaymua+"");
        holder.imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("");
                b.setMessage("Bạn có muốn xóa không?");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Del(position);
                        HoaDonAdapter.this.notifyDataSetChanged();
                    }
                });

                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog al = b.create();

                al.show();
            }

        });



    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tvloai,tvtensp,tvtenkh,tvgia,tvngay;
        ImageView imgdel;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvloai=itemView.findViewById(R.id.tv_loai_hoadon);
            tvtensp=itemView.findViewById(R.id.tv_tensp_hoadon);
            tvtenkh=itemView.findViewById(R.id.tv_tenkh);
            tvgia=itemView.findViewById(R.id.tv_gia_hoadon);
            tvngay=itemView.findViewById(R.id.tvngaynua_hoadon);
            imgdel=itemView.findViewById(R.id.phieumuon_del);
        }
    }


    public void Del(int position)
    {
        getSnapshots().getSnapshot(position).getRef().removeValue();
        Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();

    }


}