package com.wzdsqyy.applib.ui.helper;

import android.support.v4.view.ViewPager;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/10/13.
 */

public class ViewPagerHelper {
    private static Field mScrollField;
    static {
        try {
            mScrollField = ViewPager.class.getDeclaredField("mScroller");
            mScrollField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
