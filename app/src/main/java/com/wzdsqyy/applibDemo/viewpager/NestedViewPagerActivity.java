package com.wzdsqyy.applibDemo.viewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.pageadapter.NestedViewPagerAdapter;

public class NestedViewPagerActivity extends AppCompatActivity {

    private ViewPager top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
        top= (ViewPager) findViewById(R.id.topViewPager);
        top.setAdapter(NestedViewPagerAdapter.newInstance(getSupportFragmentManager()));
    }
}
