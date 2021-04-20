package com.ps14483.project1_team3;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ps14483.project1_team3.model.User;

public class CreateNewUserActivity extends AppCompatActivity {

    TextInputLayout tilUserName,tilName,tilPass,tilRePass;
    EditText edUserName,edName,edPass,edRePass;
    Button btnCreate;
    
//  Firebase
    FirebaseDatabase database;
    DatabaseReference reference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        tilName=findViewById(R.id.tilName);
        tilUserName=findViewById(R.id.tilUserName);
        tilPass=findViewById(R.id.tilPass);
        tilRePass=findViewById(R.id.tilRePass);
        edName=findViewById(R.id.edName);
        edUserName=findViewById(R.id.edUserName);
        edPass=findViewById(R.id.edPass);
        edRePass=findViewById(R.id.edRePass);
        btnCreate=findViewById(R.id.btnCreateUser);
        //kiểm lỗi nhập
        KiemLoiNhap();
//set sự kiện click
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("PetShop").child("user");

                String name, userName,pass, rePass;
                name = edName.getText().toString().trim();
                userName = edUserName.getText().toString().trim();
                pass = edPass.getText().toString().trim();
                rePass = edRePass.getText().toString().trim();

                Query userNameCheck = FirebaseDatabase.getInstance().getReference("PetShop")
                        .child("user").orderByChild("username").equalTo(userName);

                userNameCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 0) {
                            tilUserName.setError("Tài khoản bị trùng!");
                            return;
                        }else {

                            if(name.length() < 1)
                            {
                                tilName.setError("Tên không được bỏ trống!");
                                return ;
                            }
                            if(userName.length() < 5)
                            {
                                tilUserName.setError("Tài khoản ít nhất 5 ký tự!");
                                return ;
                            }
                            if(pass.length() <6 )
                            {
                                tilPass.setError("Mật khẩu ít nhất 6 ký tự!");
                                return ;
                            }
                            if(!rePass.equals(pass))
                            {
                                tilRePass.setError("Mật khẩu không Khớp!");
                                return ;
                            }


                            User item = new User(name, userName, pass);

                            reference.child(userName).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CreateNewUserActivity.this,"Tạo Thành Công",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }
    public  void  KiemLoiNhap()
    {
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edName.length()==0){
                    tilName.setError("Tên không được bỏ trống!");
                }else {
                    tilName.setErrorEnabled(false);
                }
            }
        });

        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edUserName.length() < 5 ){
                    tilUserName.setError("Tên đăng nhập ít nhất 5 ký tự!");
                }else {
                    tilUserName.setErrorEnabled(false);
                }
            }
        });

        edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edPass.length() < 6 ) {
                    tilPass.setError("Mật khẩu ít nhất 6 ký tự!");
                }else {
                    tilPass.setErrorEnabled(false);
                }
            }
        });

        edRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!edRePass.getText().toString().equalsIgnoreCase(edPass.getText().toString())){
                    tilRePass.setError("Mật khẩu nhập lại không khớp!");
                }else {
                    tilRePass.setErrorEnabled(false);
                }
            }
        });
    }
}