package com.wzdsqyy.fragment;

import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2016/11/1.
 */
class OnBackStackChangedListenerInner implements FragmentManager.OnBackStackChangedListener {

    private OnNavBackStackListener onNavBackStackListener;
    private FragmentManager manager;
    public OnBackStackChangedListenerInner(FragmentManager manager) {
        this.manager = manager;
    }

    @Override
    public void onBackStackChanged() {
        if (manager != null) {
            if (onNavBackStackListener != null) {
                onNavBackStackListener.onBackStackChanged(manager);
            }
        }
    }

    public void setOnNavBackStackListener(OnNavBackStackListener listener) {
        this.onNavBackStackListener = listener;
    }
}
