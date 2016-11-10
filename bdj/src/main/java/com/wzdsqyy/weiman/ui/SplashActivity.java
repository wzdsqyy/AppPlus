package com.wzdsqyy.weiman.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wzdsqyy.bdj.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(SplashActivity.this);
                finish();
            }
        },1500);
    }
}
