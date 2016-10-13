package com.wzdsqyy.applibDemo.viewpager.pageadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wzdsqyy.applibDemo.viewpager.fragment.InnerViewPagerFragment;

/**
 * Created by Administrator on 2016/10/13.
 */

public class NestedViewPagerAdapter extends FragmentPagerAdapter {

    public NestedViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static NestedViewPagerAdapter newInstance(FragmentManager fm) {
        NestedViewPagerAdapter fragment = new NestedViewPagerAdapter(fm);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return InnerViewPagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
