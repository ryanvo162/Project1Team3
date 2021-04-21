package com.ps14483.project1_team3.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.adapter.KhachHangAdapter;
import com.ps14483.project1_team3.model.KhachHang;

public class KhachHangFragment extends Fragment {
    Toolbar toolbar;
    ImageView imgToolbar;
    RecyclerView rcv;
    KhachHangAdapter adapter;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("PetShop");
    DatabaseReference spref=reference.child("KhachHang");
    TextInputLayout tilten,tilsdt;
    FloatingActionButton flb;
    EditText edten,edsdt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_kh, container, false);
        rcv=view.findViewById(R.id.rc_kh);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        imgToolbar = (ImageView) toolbar.findViewById(R.id.imgToolbar);
        imgToolbar.setImageResource(R.drawable.toolbar_kh);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<KhachHang> options=new FirebaseRecyclerOptions.Builder<KhachHang>()
                .setQuery(spref,KhachHang.class).build();
        adapter=new KhachHangAdapter(options,getContext());
        rcv.setAdapter(adapter);
        flb=view.findViewById(R.id.flb_kh);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });
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
    public void openDialog(Context context,int type)
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
        Button btnok=dialog.findViewById(R.id.btnOK);
        Button btncancel=dialog.findViewById(R.id.btnCancel);
        btnok.setText("ADD");
        KiemloiNhap();
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=spref.push().getKey();
                String tenkh =edten.getText().toString();
                String sdt=edsdt.getText().toString();
                //Kiểm tra trùng số
                Query sdtCheck = FirebaseDatabase.getInstance().getReference("PetShop")
                        .child("KhachHang").orderByChild("sdt").equalTo(sdt);
                sdtCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 0) {
                            tilsdt.setError("SĐT bị trùng!");
                            return;
                        }else {
                            KhachHang khachHang = new KhachHang(id,tenkh,sdt);
                            spref.child(id).setValue(khachHang).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                KhachHang khachHang = new KhachHang(id,tenkh,sdt);
//                spref.child(id).setValue(khachHang).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
            }
        });
        dialog.show();
    }
    public void KiemloiNhap()
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
