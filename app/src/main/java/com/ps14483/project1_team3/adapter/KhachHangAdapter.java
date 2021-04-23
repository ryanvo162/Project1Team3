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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.model.KhachHang;

import java.util.HashMap;

public class KhachHangAdapter extends FirebaseRecyclerAdapter<KhachHang,KhachHangAdapter.Holder> {
    private Context context;
    EditText edten,edsdt;
    TextInputLayout tilten,tilsdt;
    public String[] mColors = {"#C6D8D4","#F5D0C1"};

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public KhachHangAdapter(@NonNull FirebaseRecyclerOptions<KhachHang> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull KhachHang khachHang) {
        holder.ten.setText(khachHang.ten+"");
        holder.sdt.setText(khachHang.sdt+"");
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(khachHang.ten,khachHang.sdt,position);
                return false;
            }
        });
        holder.linearLayout.setBackgroundColor(Color.parseColor(mColors[position % 2]));

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kh,parent,false);
        return new Holder(view);
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView ten,sdt;
        ImageView imgedit,imgdel;
        CardView card;
        LinearLayout linearLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            ten=itemView.findViewById(R.id.tv_ten_kh);
            sdt=itemView.findViewById(R.id.tv_sdt);
            card=itemView.findViewById(R.id.item_kh);
            linearLayout=itemView.findViewById(R.id.linear_kh);
        }
    }
    public void update(String ten,String sdt,int position)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_kh);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_none);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        edten=dialog.findViewById(R.id.edTenkh);
        edsdt=dialog.findViewById(R.id.edsdt);
        tilten=dialog.findViewById(R.id.tilTenkh);
        tilsdt=dialog.findViewById(R.id.tilsdt);
        KiemLoiNhap();
        Button btnok=dialog.findViewById(R.id.btnOK);
        Button btncancel=dialog.findViewById(R.id.btnCancel);
        btncancel.setText("Delete");
        btnok.setText("Edit");

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("");
                b.setMessage("Bạn có muốn xóa không?");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Del(position);
                        KhachHangAdapter.this.notifyDataSetChanged();
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
        edten.setText(ten);
        edsdt.setText(sdt+"");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edten.getText().toString();
                String sdt = edsdt.getText().toString();
                if (ten.length() == 0 ||  sdt.length() != 10) {
                    Toast.makeText(context, "Bạn chưa đáp ứng đủ yêu cầu", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap hashMap = new HashMap();
                    hashMap.put("ten", ten);
                    hashMap.put("sdt", sdt);
                    getSnapshots().getSnapshot(position).getRef().updateChildren(hashMap);
                    dialog.dismiss();
                }
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

        edsdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edsdt.length()<10)
                {
                    tilsdt.setError("Số điện thoại cần 10 số");
                }else {
                    tilsdt.setErrorEnabled(false);
                }
            }
        });

    }
}
