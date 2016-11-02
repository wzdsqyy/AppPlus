package com.wzdsqyy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

/**
 * Created by Qiuyy on 2016/8/26.
 */
public abstract class NavFragment extends BaseFragment implements ContentPage{
    private OnBackStackChangedListenerInner onBackStackChangedListener;

    private OnBackStackChangedListenerInner getOnBackStackChangedListener() {
        if (onBackStackChangedListener == null) {
            onBackStackChangedListener = new OnBackStackChangedListenerInner(getPageFragmentManager());
        }
        return onBackStackChangedListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        getPageFragmentManager().addOnBackStackChangedListener(getOnBackStackChangedListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPageFragmentManager().removeOnBackStackChangedListener(getOnBackStackChangedListener());
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getChildFragmentManager();
    }
}
