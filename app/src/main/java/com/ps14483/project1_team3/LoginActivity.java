package com.ps14483.project1_team3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps14483.project1_team3.model.User;

public class LoginActivity extends AppCompatActivity {

    EditText edUserName,edPassword;
    TextInputLayout tilUser,tilPass;
    Button btnLogin, btnGuest;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName=findViewById(R.id.edUserName);
        edPassword=findViewById(R.id.edPassword);
        tilUser=findViewById(R.id.tilUser);
        tilPass=findViewById(R.id.tilPass);
        btnLogin=findViewById(R.id.btnLogin);
        btnGuest=findViewById(R.id.btnGuest);

        // Firebase user
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("PetShop").child("user");
        KiemLoiNhap();
//      Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUserName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                if (edUserName.length()==0)
                {
                    tilUser.setError("Username không được để trống");
                    return;
                }else {
                reference.child(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        try {
                            if(password.equalsIgnoreCase(user.password)){
                                Toast.makeText(LoginActivity.this,"Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("name",user.getName());
                                bundle.putString("username",user.getUsername());
                                bundle.putString("password",user.getPassword());
                                i.putExtra("User",bundle);
                                startActivity(i);
                                finish();
                            }else {
                                if (password.length()==0)
                                {
                                    tilPass.setError("Password không được để trống");
                                }else {
                                    tilPass.setError("Sai Mật Khẩu!");
                                }
                            }
                        }catch (Exception e) {
                            tilUser.setError("Sai Tài Khoản");
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }}
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,GuestActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void KiemLoiNhap() {

        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edUserName.length() == 0){
                    tilUser.setError("Username không được để trống");
                    return;
                }else {
                    tilUser.setErrorEnabled(false);
                }
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edPassword.length() == 0){
                    tilPass.setError("Password không được để trống");
                    return;
                }else {
                    tilPass.setErrorEnabled(false);
                }
            }
        });
    }
}