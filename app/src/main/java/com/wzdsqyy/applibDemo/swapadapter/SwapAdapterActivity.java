package com.wzdsqyy.applibDemo.swapadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.commonview.Banner;

public class SwapAdapterActivity extends AppCompatActivity implements Banner.BannerListener {

    private static final String TAG = "SwapAdapterActivity";
    Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_adapter);
        banner= (Banner) findViewById(R.id.banner);
        banner.setCount(4);
        banner.setBannerListener(this);
    }

    public void showNext(View view){
        banner.showNext();
    }

    public void showPrevious(View view){
        banner.showPrevious();
    }

    @Override
    public void onPageScrolled(int position, float offset, ImageView mIvLeft) {
        Log.d(TAG, "onPageScrolled() called wi" +
                "th: position = [" + position + "], offset = [" + offset + "], mIvLeft = [" + mIvLeft + "]");
    }

    @Override
    public void onPageSelected(int position, ImageView view) {
        int color;
        switch (position){
            case 1:
                color=0xffff0000;
                break;
            case 2:
                color=0xff00ff00;
                break;
            case 3:
                color=0xff0000ff;
                break;
            default:
                color=0xff000000;
                break;
        }
        view.setBackgroundColor(color);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.d(TAG, "onPageScrollStateChanged() called with: state = [" + state + "]");
    }
}
