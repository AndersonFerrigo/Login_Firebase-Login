package com.example.myapplication.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.example.myapplication.R;
import com.example.myapplication.views.LoginActivity;

public class SplashScreenHomeSchoolActivity extends AppCompatActivity {

    LinearLayout containerLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_home_school);

        containerLL = findViewById(R.id.container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        TashieLoader tashie = new TashieLoader(
                this, 0,
                5, 10,
                ContextCompat.getColor(this, R.color.cor_branca));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(4000);
        tashie.setInterpolator(new LinearInterpolator());
        containerLL.addView(tashie);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostraLogin();
            }
        }, 4000);
    }

    private void mostraLogin() {
        Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }
  }


