package com.wzdsqyy.fragment;

import android.support.v4.app.FragmentManager;

/**
 * Created by Qiuyy on 2016/8/26.
 */
public abstract class TabbarFragment extends BaseFragment implements ContentPage {
    @Override
    public FragmentManager getPageFragmentManager() {
        return getChildFragmentManager();
    }
}
