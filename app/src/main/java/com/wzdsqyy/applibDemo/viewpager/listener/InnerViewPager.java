package com.wzdsqyy.applibDemo.viewpager.listener;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.wzdsqyy.applib.ui.nested.DisallowInterceptListener;
import com.wzdsqyy.applib.ui.nested.NestedHelper;

/**
 * Created by Administrator on 2016/10/13.
 */

public class InnerViewPager extends ViewPager.SimpleOnPageChangeListener implements DisallowInterceptListener {
    private static final String TAG = "InnerViewPager";
    private ViewPager pager;
    private boolean Intercept = false;

    public InnerViewPager(@NonNull ViewPager pager) {
        this.pager = pager;
        pager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0 | position == pager.getAdapter().getCount() - 1) {
            Intercept = false;
        } else {
            Intercept = true;
        }
    }

    @Override
    public boolean disallowInterceptTouchEvent(NestedHelper v, MotionEvent event) {
        return Intercept;
    }
}
