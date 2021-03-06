package com.ps14483.project1_team3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.ps14483.project1_team3.fragment.ChoFragment;
import com.ps14483.project1_team3.fragment.DoChoiFragment;
import com.ps14483.project1_team3.fragment.HoaDonFragment;
import com.ps14483.project1_team3.fragment.KhachHangFragment;
import com.ps14483.project1_team3.fragment.MeoFragment;
import com.ps14483.project1_team3.fragment.PetFragment;
import com.ps14483.project1_team3.fragment.SanPhamFragment;
import com.ps14483.project1_team3.fragment.TaiKhoanFragment;
import com.ps14483.project1_team3.fragment.ThucAnFragment;

public class MainActivity extends AppCompatActivity {
    TextView nav_name;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigation_view;
    FragmentManager fragmentManager;
    public String username, password, name;
    FragmentTransaction ft;
    ActionBarDrawerToggle bdtoggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigation_view = findViewById(R.id.new_nav);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("User");
        username = bundle.getString("username", "");
        password = bundle.getString("password", "");
        name = bundle.getString("name", "");
        nav_name=findViewById(R.id.tvName);
        nav_name.setText(name);
        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);
        bdtoggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_nav, R.string.close_nave);
        drawer.addDrawerListener(bdtoggle);
        bdtoggle.syncState();

//        navigation_view.getMenu();

        fragmentManager.beginTransaction().add(R.id.frame_layout, new PetFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) ;
        drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bdtoggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        bdtoggle.onConfigurationChanged(newConfig);
    }

    public void logout() {
        Snackbar.make(findViewById(R.id.frame_layout), "B???n c?? mu???n ????ng xu???t", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("Tho??t", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();

    }

    @SuppressLint("ResourceAsColor")
    public void onClickPet(View view) {
        toolbar.setTitle("Th?? C??ng");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new PetFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

    }

    public void onClickTaiKhoan(View view) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new TaiKhoanFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

    }

    public void onClickSanPham(View view) {
        toolbar.setTitle("S???n Ph???m");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new SanPhamFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

    }

    public void onClickHoaDon(View view) {
        toolbar.setTitle("H??a ????n");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new HoaDonFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

    }

    public void onClickKhachHang(View view) {
        toolbar.setTitle("Kh??ch H??ng");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new KhachHangFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

    }

    public void logOut(View view) {
        logout();
    }

}