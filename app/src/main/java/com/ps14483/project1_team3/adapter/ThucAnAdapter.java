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
import com.ps14483.project1_team3.model.ThucAn;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ThucAnAdapter extends FirebaseRecyclerAdapter<ThucAn,ThucAnAdapter.Holder> {
    private Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ThucAnAdapter(@NonNull FirebaseRecyclerOptions<ThucAn> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull ThucAn thucAn) {
        holder.ten.setText(thucAn.ten+"");
        holder.gia.setText(thucAn.gia+"");
        holder.chitiet.setText(thucAn.chitiet);
        String imgUri=thucAn.img+"";
        Picasso.get().load(imgUri).into(holder.imguri);

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(thucAn.ten,thucAn.chitiet,thucAn.gia,position);
                return false;
            }
        });

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView ten,gia,chitiet;
        ImageView imgedit,imgdel,imguri;
        CardView card;
        public Holder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.tv_ten_sp);
            gia=itemView.findViewById(R.id.tv_giasp);
            imguri=itemView.findViewById(R.id.img_sp);
            card=itemView.findViewById(R.id.item_sp);
            chitiet=itemView.findViewById(R.id.tv_chitietsp);
        }
    }
    public void update(String ten,String chitiet,int gia,int position)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_sp);
        EditText edten=dialog.findViewById(R.id.edTen);
        EditText edchitiet=dialog.findViewById(R.id.edchitietsp);
        EditText edgia=dialog.findViewById(R.id.edgia);
        Button btnok=dialog.findViewById(R.id.btnOK);
        Button btncancel=dialog.findViewById(R.id.btnCancel);
        ImageView imageView=dialog.findViewById(R.id.dialog_img_sp);
        ((ViewGroup)imageView.getParent()).removeView(imageView);
        btncancel.setText("Delete");
        btnok.setText("Edit");
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edten.setText(ten);
        edgia.setText(gia+"");
        edchitiet.setText(chitiet+"");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=edten.getText().toString();
                int gia=Integer.parseInt(edgia.getText().toString());
                String chitiet=edchitiet.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("ten",ten);
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
                        ThucAnAdapter.this.notifyDataSetChanged();
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
