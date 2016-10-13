package com.wzdsqyy.applibDemo.viewpager.pageadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wzdsqyy.applibDemo.viewpager.fragment.InnerListViewFragment;

/**
 * Created by Administrator on 2016/10/13.
 */

public class NestedListViewAdapter extends FragmentPagerAdapter {

    public NestedListViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public static NestedListViewAdapter newInstance(FragmentManager fm) {
        NestedListViewAdapter fragment = new NestedListViewAdapter(fm);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return InnerListViewFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
