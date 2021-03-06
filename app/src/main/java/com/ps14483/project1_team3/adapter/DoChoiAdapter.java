package com.ps14483.project1_team3.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.model.Dochoi;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DoChoiAdapter extends FirebaseRecyclerAdapter<Dochoi,DoChoiAdapter.Holder> {
    EditText edten,edgia,edchitiet;
    TextInputLayout tilten,tilgia;
    private Context context;
    public String[] mColors = {"#F5D0C1","#C6D8D4"};
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DoChoiAdapter(@NonNull FirebaseRecyclerOptions<Dochoi> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Dochoi dochoi) {
        holder.ten.setText(dochoi.ten+"");
        holder.gia.setText(dochoi.gia+"");
        holder.chitiet.setText(dochoi.chitiet);
        String imgUri=dochoi.img+"";
        Picasso.get().load(imgUri).into(holder.imguri);

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(dochoi.ten,dochoi.chitiet,dochoi.gia,position);
                return false;
            }
        });

        holder.linearLayout.setBackgroundColor(Color.parseColor(mColors[position % 2]));
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
        LinearLayout linearLayout;
        CardView card;
        public Holder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.tv_ten_sp);
            gia=itemView.findViewById(R.id.tv_giasp);
            imguri=itemView.findViewById(R.id.img_sp);
            card=itemView.findViewById(R.id.item_sp);
            chitiet=itemView.findViewById(R.id.tv_chitietsp);
            linearLayout=itemView.findViewById(R.id.linear_sp);
        }
    }
    public void update(String ten,String chitiet,int gia,int position)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_sp);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_none);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        edten=dialog.findViewById(R.id.edTen);
        edchitiet=dialog.findViewById(R.id.edchitietsp);
        edgia=dialog.findViewById(R.id.edgia);
        tilgia=dialog.findViewById(R.id.tilgiasp);
        tilten=dialog.findViewById(R.id.tilTensp);
        Button btnok=dialog.findViewById(R.id.btnOK);
        Button btncancel=dialog.findViewById(R.id.btnCancel);
        RelativeLayout relativeLayout=dialog.findViewById(R.id.phan_chon_hinh_sp);
        ((ViewGroup)relativeLayout.getParent()).removeView(relativeLayout);
        KiemLoiNhap();
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
                String ten = edten.getText().toString();

                String gia1 = edgia.getText().toString();
                String chitiet = edchitiet.getText().toString();
                if (ten.length()==0 || gia1.length() < 4) {
                    Toast.makeText(context, "B???n ch??a ????p ???ng ????? y??u c???u", Toast.LENGTH_SHORT).show();
                } else {
                    int gia = Integer.parseInt(gia1);
                    HashMap hashMap = new HashMap();
                    hashMap.put("ten", ten);
                    hashMap.put("gia", gia);
                    hashMap.put("chitiet", chitiet);
                    getSnapshots().getSnapshot(position).getRef().updateChildren(hashMap);
                    dialog.dismiss();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("");
                b.setMessage("B???n c?? mu???n x??a kh??ng?");
                b.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Del(position);
                        DoChoiAdapter.this.notifyDataSetChanged();
                    }
                });

                b.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
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
    }
    public void KiemLoiNhap()
    {
        edten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edten.length()==0)
                {
                    tilten.setError("T??n kh??ng ???????c b??? tr???ng");
                }else {
                    tilten.setErrorEnabled(false);
                }
            }
        });

        edgia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edgia.length()<4)
                {
                    tilgia.setError("Gi?? t???i thi???u l?? 1000");
                }else {
                    tilgia.setErrorEnabled(false);
                }
            }
        });

    }
}
