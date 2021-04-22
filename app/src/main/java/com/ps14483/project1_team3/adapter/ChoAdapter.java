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
import com.ps14483.project1_team3.fragment.ChoFragment;
import com.ps14483.project1_team3.model.Cho;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ChoAdapter extends FirebaseRecyclerAdapter<Cho,ChoAdapter.Holder> {
    private Context context;
    EditText edten,edgia,edchitiet,edmaulong;
    TextInputLayout tilten,tilgia,tilmaulong;
    public String[] mColors = {"#F5D0C1","#C6D8D4"};
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
        holder.linearLayout.setBackgroundColor(Color.parseColor(mColors[position % 2]));


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
        LinearLayout linearLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.item_cho_meo);
            ten=itemView.findViewById(R.id.tv_ten_cho_meo);
            gia=itemView.findViewById(R.id.tv_giapet);
            maulong=itemView.findViewById(R.id.tv_maulong);
            chitiet=itemView.findViewById(R.id.tv_chitietpet);
            imguri =itemView.findViewById(R.id.img_pet);
            linearLayout=itemView.findViewById(R.id.linear_meo);
        }
    }
    public void update(String ten,String maulong,String chitiet,int gia,int position)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_chomeo);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_none);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        edten=dialog.findViewById(R.id.edTenpet);
        edmaulong=dialog.findViewById(R.id.edmaulong);
        edgia=dialog.findViewById(R.id.edgiapet);
        tilten=dialog.findViewById(R.id.tilTenpet);
        tilmaulong=dialog.findViewById(R.id.tilmaulong);
        tilgia=dialog.findViewById(R.id.tilgiapet);
        edchitiet=dialog.findViewById(R.id.edchitiet);
        Button btnok=dialog.findViewById(R.id.btnOKpet);
        Button btncancel=dialog.findViewById(R.id.btnCancelpet);
        RelativeLayout relativeLayout=dialog.findViewById(R.id.phan_chon_hinh_pet);
        ((ViewGroup)relativeLayout.getParent()).removeView(relativeLayout);
        KiemLoiNhap();
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
                String ten = edten.getText().toString();
                String maulong = edmaulong.getText().toString();
                String gia1 = edgia.getText().toString();
                String chitiet = edchitiet.getText().toString();
                if (ten.length()<0 || maulong.length()<0 || gia1.length() < 4 ) {
                    Toast.makeText(context, "Bạn chưa đáp ứng đủ yêu cầu", Toast.LENGTH_SHORT).show();
                } else {
                    int gia=Integer.parseInt(gia1);
                    HashMap hashMap = new HashMap();
//                Map<String,Object> hashMap=new HashMap<>();
                    hashMap.put("ten", ten);
                    hashMap.put("maulong", maulong);
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
                    tilten.setError("Tên không được bỏ trống");
                }else {
                    tilten.setErrorEnabled(false);
                }
            }
        });
        edmaulong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edmaulong.length()==0)
                {
                    tilmaulong.setError("Màu lông không được bỏ trống");
                }else {
                    tilmaulong.setErrorEnabled(false);
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
                    tilgia.setError("Giá tối thiểu là 1000");
                }else {
                    tilgia.setErrorEnabled(false);
                }
            }
        });

    }
}

