package com.ps14483.project1_team3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.ps14483.project1_team3.ChangePassActivity;
import com.ps14483.project1_team3.CreateNewUserActivity;
import com.ps14483.project1_team3.LoginActivity;
import com.ps14483.project1_team3.MainActivity;
import com.ps14483.project1_team3.R;

public class TaiKhoanFragment extends Fragment {
    TextView tentk,loaitk,logout;
    Button taotk,doipass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_taikhoan,container,false);
        tentk=view.findViewById(R.id.tvTen);
        loaitk=view.findViewById(R.id.tvLoaiTK);
        taotk=view.findViewById(R.id.btntaoTK);
        doipass=view.findViewById(R.id.btndoipass);
        logout=view.findViewById(R.id.btnThoat);
        //lấy tên

        tentk.setText(((MainActivity)getActivity()).name);
        if (((MainActivity)getActivity()).username.equals("admin"))
        {
            loaitk.setText("ADMIN");
        }else
        {
            loaitk.setText("Nhân Viên");
        }
        if (!loaitk.getText().toString().equals("ADMIN"))
        {
            ((ViewGroup)taotk.getParent()).removeView(taotk);

        }
        taotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),CreateNewUserActivity.class);
                startActivity(intent);
            }
        });
        doipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),ChangePassActivity.class);
                i.putExtra("tk",((MainActivity)getActivity()).username);
                i.putExtra("mk",((MainActivity)getActivity()).password);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "Bạn có muốn đăng xuất", BaseTransientBottomBar.LENGTH_SHORT)
                        .setAction("Thoát", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }).show();
            }
        });
        return view;
    }

}
