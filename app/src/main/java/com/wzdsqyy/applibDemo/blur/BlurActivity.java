package com.wzdsqyy.applibDemo.blur;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wzdsqyy.applib.drawable.BlurDrawable;
import com.wzdsqyy.applibDemo.R;

public class BlurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        View view = findViewById(R.id.imageView3);
        BlurDrawable drawable = new BlurDrawable(view);
//模糊半径，越大图片越平均
        drawable.setBlurRadius(12);
//图片抽样率，这里把图片缩放小了8倍
        drawable.setDownsampleFactor(16);
//模糊后再覆盖的一层颜色
        drawable.setOverlayColor(Color.argb(0x0f, 0x00, 0x00, 0x00));
//顶部View与底部View的相对坐标差，由于这里都是(0,0)起步
//所以相对位置偏移为0
        drawable.setDrawOffset(0, 0);
        findViewById(R.id.bulrview).setBackgroundDrawable(drawable);


        AsyncTask.THREAD_POOL_EXECUTOR





    }
}
