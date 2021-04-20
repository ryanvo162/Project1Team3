package com.ps14483.project1_team3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangePassActivity extends AppCompatActivity {

    TextInputLayout tilPass,tilNewPass,tilReNewPass;
    EditText edPass,edNewPass,edReNewPass;
    Button btn;
    String pass,newPass,newRePass;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        tilPass=findViewById(R.id.tilPass);
        tilNewPass=findViewById(R.id.tilNewPass);
        tilReNewPass=findViewById(R.id.tilReNewPass);
        edPass=findViewById(R.id.edPass);
        edNewPass=findViewById(R.id.edNewPass);
        edReNewPass=findViewById(R.id.edReNewPass);
        btn=findViewById(R.id.btnDoiPass);

        //Lấy dữ liệu
        String username=getIntent().getStringExtra("tk");
        String password=getIntent().getStringExtra("mk");
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("PetShop").child("user");

        //Button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass = edPass.getText().toString();
                newPass = edNewPass.getText().toString();
                newRePass = edReNewPass.getText().toString();

                if(!pass.equalsIgnoreCase(pass)){
                    tilPass.setError("Mật khẩu cũ không đúng!");
                    return;
                }else {
                    tilPass.setErrorEnabled(false);
                }
                if(newPass.length() < 6){
                    tilNewPass.setError("Mật khẩu ít nhất 6 ký tự!");
                    return;
                }else {
                    tilNewPass.setErrorEnabled(false);
                }
                if (!newRePass.equals(newPass)){
                    tilReNewPass.setError("Mật khẩu mới không khớp!");
                    return;
                }else {
                    tilReNewPass.setErrorEnabled(false);
                }

                HashMap hashMap = new HashMap();
                hashMap.put("pass",newPass);
                reference.child(username).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(ChangePassActivity.this,"Đổi Mật Khẩu Thành Công!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

            }
        });
    }
}