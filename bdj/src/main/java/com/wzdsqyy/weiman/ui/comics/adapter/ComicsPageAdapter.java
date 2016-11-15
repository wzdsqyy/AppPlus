package com.wzdsqyy.weiman.ui.comics.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.data.model.ModelManager;
import com.wzdsqyy.weiman.ui.comics.fragment.ComicsPageFragment;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsPageAdapter extends FragmentPagerAdapter {
    private ComicsModel model;

    public ComicsPageAdapter(FragmentManager fm, FragmentActivity activity) {
        super(fm);
        try {
            model = ModelManager.getModel(ComicsModel.class,activity.getSupportFragmentManager());
        } catch (Exception e) {
           model=null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return ComicsPageFragment.newInstance(model.getNames().get(position));
    }

    @Override
    public int getCount() {
        if(model==null){
            return 0;
        }
        return model.getNames().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return model.getNames().get(position);
    }
}
