package com.ps14483.project1_team3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.ps14483.project1_team3.fragment.GuestScreenFragment;
import com.ps14483.project1_team3.fragment.Guest_PetFragment;
import com.ps14483.project1_team3.fragment.Guest_SanPhamFragment;
import com.ps14483.project1_team3.fragment.HoaDonFragment;
import com.ps14483.project1_team3.fragment.KhachHangFragment;
import com.ps14483.project1_team3.fragment.PetFragment;
import com.ps14483.project1_team3.fragment.SanPhamFragment;
import com.ps14483.project1_team3.fragment.TaiKhoanFragment;

public class GuestActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigation_view;
    FragmentManager fragmentManager;
    public String username, name;
    ActionBarDrawerToggle bdtoggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigation_view = findViewById(R.id.new_nav);

        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);
        bdtoggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_nav, R.string.close_nave);
        drawer.addDrawerListener(bdtoggle);
        bdtoggle.syncState();
        fragmentManager.beginTransaction().add(R.id.guest_frame_layout, new GuestScreenFragment()).commit();
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
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
        Snackbar.make(findViewById(R.id.guest_frame_layout), "Bạn có muốn thoát", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("Thoát", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GuestActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }).show();

    }

    public void onClickThuCung(View view) {
        toolbar.setTitle("Thú Cưng");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.guest_frame_layout, new Guest_PetFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    public void onClickSp(View view) {
        toolbar.setTitle("Sản Phẩm");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.guest_frame_layout, new Guest_SanPhamFragment()).commit();
        drawer.closeDrawer(GravityCompat.START);
    }
    public void getStarted(View view) {
        toolbar.setTitle("Thú Cưng");
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        fragmentManager.beginTransaction().replace(R.id.guest_frame_layout, new Guest_PetFragment()).commit();
    }

    public void Thoat(View view) {
        logout();
    }

}