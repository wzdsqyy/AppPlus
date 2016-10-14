package com.wzdsqyy.applibDemo.loop;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.fragment.*;
import com.wzdsqyy.applibDemo.viewpager.pageadapter.NestedViewPagerAdapter;

public class LooperActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private static final String TAG = "LooperActivity";

    private ViewPager top;
    private MyAdapter adapter;
    private Toast toast;
    private boolean isStop=true;
    private boolean isLeft=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        top= (ViewPager) findViewById(R.id.topViewPager);
        adapter=new MyAdapter(top,4);
        top.setAdapter(adapter);
        adapter.addOnPageChangeListener(this);
        adapter.setCurrentItem(0);
    }

    public void onStartOrStop(final View view){
        this.isStop=!isStop;
        if(!isStop){
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(isLeft){
                        adapter.switchToNextPage();
                    }else {
                        adapter.switchToPrePage();
                    }
                    if(!isStop){
                        view.postDelayed(this,1000);
                    }
                }
            },1000);
        }
    }

    public void changeOrientation(View view){
        this.isLeft=!isLeft;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled() called with: position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");
    }

    @Override
    public void onPageSelected(int position) {
        if(toast!=null){
            toast.cancel();
        }
        toast = Toast.makeText(this, "position" + position, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.d(TAG, "onPageScrollStateChanged() called with: state = [" + state + "]");
    }
}
