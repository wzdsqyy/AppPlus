package com.wzdsqyy.applibDemo.swapadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.wzdsqyy.applibDemo.R;

import okhttp3.internal.cache.DiskLruCache;

public class SwapAdapterActivity extends AppCompatActivity {

    DiskLruCache lruCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_adapter);
    }
}
