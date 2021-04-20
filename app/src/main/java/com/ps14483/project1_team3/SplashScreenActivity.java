package com.ps14483.project1_team3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity {
    private static final int splash_time_out = 2000;

    ImageView logoHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            finish();
        }, splash_time_out);
//        Animation logo
        logoHello = findViewById(R.id.logoHello);
        Animation animationZoom = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        logoHello.startAnimation(animationZoom);
    }
}