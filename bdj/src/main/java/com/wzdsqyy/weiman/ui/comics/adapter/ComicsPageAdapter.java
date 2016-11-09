package com.wzdsqyy.weiman.ui.comics.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.ui.comics.fragment.ComicsPageFragment;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsPageAdapter extends FragmentPagerAdapter {
    public ComicsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ComicsPageFragment.newInstance(ComicsModel.getNames().get(position));
    }

    @Override
    public int getCount() {
        return ComicsModel.getNames().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ComicsModel.getNames().get(position);
    }
}
