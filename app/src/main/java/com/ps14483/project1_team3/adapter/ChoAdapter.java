package com.ps14483.project1_team3.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.fragment.ChoFragment;
import com.ps14483.project1_team3.model.Cho;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ChoAdapter extends FirebaseRecyclerAdapter<Cho,ChoAdapter.Holder> {
    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChoAdapter(@NonNull FirebaseRecyclerOptions<Cho> options, Context context) {
        super(options);
        this.context=context;
        ChoFragment fragment=new ChoFragment();
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Cho cho) {
        holder.ten.setText(cho.ten+"");
        holder.gia.setText(cho.gia+"");
        holder.maulong.setText(cho.maulong+"");
        holder.chitiet.setText(cho.chitiet+"");
        String imgUri=cho.img+"";
        Picasso.get().load(imgUri).into(holder.imguri);
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(cho.ten,cho.maulong,cho.chitiet,cho.gia,position);
                return false;
            }
        });


    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cho_meo,parent,false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView ten,gia,maulong,chitiet;
        ImageView imgedit,imgdel, imguri;
        CardView card;

        public Holder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.item_cho_meo);
            ten=itemView.findViewById(R.id.tv_ten_cho_meo);
            gia=itemView.findViewById(R.id.tv_giapet);
            maulong=itemView.findViewById(R.id.tv_maulong);
            chitiet=itemView.findViewById(R.id.tv_chitietpet);
            imguri =itemView.findViewById(R.id.img_pet);
        }
    }
    public void update(String ten,String maulong,String chitiet,int gia,int position)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_chomeo);
        EditText edten=dialog.findViewById(R.id.edTenpet);
        EditText edmaulong=dialog.findViewById(R.id.edmaulong);
        EditText edgia=dialog.findViewById(R.id.edgiapet);
        EditText edchitiet=dialog.findViewById(R.id.edchitiet);
        Button btnok=dialog.findViewById(R.id.btnOKpet);
        Button btncancel=dialog.findViewById(R.id.btnCancelpet);
        ImageView img=dialog.findViewById(R.id.dialog_img_pet);
        ((ViewGroup)img.getParent()).removeView(img);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btncancel.setText("Delete");
        btnok.setText("Edit");
        edten.setText(ten);
        edmaulong.setText(maulong);
        edgia.setText(gia+"");
        edchitiet.setText(chitiet+"");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=edten.getText().toString();
                String maulong=edmaulong.getText().toString();
                int gia=Integer.parseInt(edgia.getText().toString());
                String chitiet=edchitiet.getText().toString();
                HashMap hashMap = new HashMap();
//                Map<String,Object> hashMap=new HashMap<>();
                hashMap.put("ten",ten);
                hashMap.put("maulong",maulong);
                hashMap.put("gia",gia);
                hashMap.put("chitiet",chitiet);
                getSnapshots().getSnapshot(position).getRef().updateChildren(hashMap);
                dialog.dismiss();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("");
                b.setMessage("Bạn có muốn xóa không?");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Del(position);
                        ChoAdapter.this.notifyDataSetChanged();

                    }
                });

                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog al = b.create();

                al.show();
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void Del(int position)
    {
        getSnapshots().getSnapshot(position).getRef().removeValue();
        Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();

    }

}

