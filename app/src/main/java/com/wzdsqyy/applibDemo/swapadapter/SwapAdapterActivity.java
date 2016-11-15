package com.wzdsqyy.applibDemo.swapadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wzdsqyy.applibDemo.R;

public class SwapAdapterActivity extends AppCompatActivity{

    private static final String TAG = "SwapAdapterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_adapter);
//        banner= (Banner) findViewById(R.id.banner);
//        banner.setCount(4);
//        banner.setBannerListener(this);
    }

    public void showNext(View view){
//        banner.showNext();
    }

    public void showPrevious(View view){
//        banner.showPrevious();
    }
}
