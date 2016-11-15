package com.wzdsqyy.applibDemo;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.wzdsqyy.applibDemo.viewpager.pageadapter.NestedListViewAdapter;
import com.wzdsqyy.commonview.drawable.BadgeDrawable;
import com.wzdsqyy.commonview.drawable.OnTabSelectListener;
import com.wzdsqyy.commonview.drawable.TabDrawable;

public class TabTestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnTabSelectListener {
    private static final String TAG = "TabTestActivity";
    private ViewPager pager;
    private TabDrawable tabDrawable;
    private LinearLayout tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);
        tab= (LinearLayout) findViewById(R.id.activity_tab);
        pager= (ViewPager) findViewById(R.id.main_page_containr);
        pager.setAdapter(new NestedListViewAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(this);
        tabDrawable=TabDrawable.setTabsContainer(tab);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabDrawable.setCurrentTab(position,true);
//        tabDrawable.stopMoveing();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        Log.d(TAG, "onTabSelect() called with: position = [" + position + "]");
    }

    @Override
    public void onTabOffset(int position, float radio) {
        Log.d(TAG, "onTabOffset() called with: position = [" + position + "], radio = [" + radio + "]");
    }
}
