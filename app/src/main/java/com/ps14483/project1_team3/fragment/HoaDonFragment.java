package com.ps14483.project1_team3.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14483.project1_team3.R;
import com.ps14483.project1_team3.adapter.HoaDonAdapter;
import com.ps14483.project1_team3.model.Hoadon;

import java.util.ArrayList;
import java.util.Calendar;

public class HoaDonFragment extends Fragment {
    Toolbar toolbar;
    ImageView imgToolbar;
    private Context mContext;
    RecyclerView rcv;
    HoaDonAdapter adapter;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("PetShop");
    DatabaseReference ref1;
    TextView tvNgay,tvgia;
    FloatingActionButton flb;
    Spinner sploaisp,sptensp,sptenkh;
    Button btnok,btncancel;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> tensp1=new ArrayList<>();
    ArrayList<String>kh=new ArrayList<>();
    ArrayList<String> gia1=new ArrayList<>();
    String gia;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_hoadon, container, false);
        rcv=view.findViewById(R.id.rc_hoadon);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        imgToolbar = (ImageView) toolbar.findViewById(R.id.imgToolbar);
        imgToolbar.setImageResource(R.drawable.toolbar_hd);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Hoadon> options=new FirebaseRecyclerOptions.Builder<Hoadon>()
                .setQuery(reference.child("HoaDon"),Hoadon.class).build();
        adapter=new HoaDonAdapter(options,getContext());
        rcv.setAdapter(adapter);
        flb=view.findViewById(R.id.flb_hoadon);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });
        return view ;
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
        Dialog dialog= new Dialog(context);
        dialog.setContentView(R.layout.dialog_hoadon);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_none);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        sploaisp=dialog.findViewById(R.id.spinner_loaisp);
        sptensp=dialog.findViewById(R.id.spinner_tensp);
        sptenkh=dialog.findViewById(R.id.spinner_kh);
        tvgia=dialog.findViewById(R.id.dialog_gia_hoadon);
        tvNgay=dialog.findViewById(R.id.tvngaymua);
        ImageView imgngay=dialog.findViewById(R.id.ivngay);
        btnok=dialog.findViewById(R.id.pm_btnOK_hoadon);
        btncancel=dialog.findViewById(R.id.pm_btnCancel_hoadon);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imgngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker();
            }
        });
        showloaispSpinner();
        showkhSpinner();
        btnok.setText("ADD");

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id=reference.child("HoaDon").push().getKey();
                String loaisp=arrayList.get(sploaisp.getSelectedItemPosition());
                String tensp= tensp1.get(sptensp.getSelectedItemPosition());
                String kh1=kh.get(sptenkh.getSelectedItemPosition());
                String ngay1=tvNgay.getText().toString();

                int giamua1=Integer.parseInt(tvgia.getText().toString());
                Hoadon hoadon=new Hoadon(id,loaisp,tensp,kh1,ngay1,giamua1);
                reference.child("HoaDon").child(id).setValue(hoadon).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }

        });
        dialog.show();;
    }
    public void showloaispSpinner()
    {
        reference.child("sanpham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot item: snapshot.getChildren())
                {
                    arrayList.add(item.getKey());
                }
                ArrayAdapter<String>adapter=new ArrayAdapter<>(mContext,R.layout.spinner_item,arrayList);
                sploaisp.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                sploaisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selected=parent.getItemAtPosition(position).toString();
                        reference.child("sanpham").child(selected).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                tensp1.clear();
                                gia1.clear();
                                for (DataSnapshot item: snapshot.getChildren())
                                {
                                    tensp1.add(item.child("ten").getValue(String.class));
                                    gia1.add(item.child("key").getValue().toString());

                                }
                                ArrayAdapter<String>adapter=new ArrayAdapter<>(mContext,R.layout.spinner_item,tensp1);

                                sptensp.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                sptensp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String a=gia1.get(position).toString();
                                        String select1=parent.getItemAtPosition(position).toString();
                                        DatabaseReference rgia=reference.child("sanpham").child(selected).child(a);
                                        rgia.child("gia").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                tvgia.setText(snapshot.getValue().toString());
                                                Log.d("giah", tvgia+"");
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Log.d("gia", a+""+gia1+""+gia);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showkhSpinner()
    {
        reference.child("KhachHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                kh.clear();
                for (DataSnapshot item: snapshot.getChildren())
                {
                    kh.add(item.child("ten").getValue(String.class));
                }
                ArrayAdapter<String>adapter=new ArrayAdapter<>(mContext,R.layout.spinner_item,kh);
                sptenkh.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getDatePicker()
    {
        Calendar calendar=Calendar.getInstance();
        int YEAR= calendar.get(Calendar.YEAR);
        int MONTH= calendar.get(Calendar.MONTH);
        int DATE= calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dateofMonth) {
                String date=year+"-"+(month+1)+"-"+dateofMonth;
                tvNgay.setText(date);
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();

    }
}
