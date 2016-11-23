package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;

import com.wzdsqyy.fragment.internal.ManagerProvider;

/**
 * Created by Administrator on 2016/11/3.
 */

public interface Manager {
    ManagerProvider getManagerProvider();

    boolean onBackPressed();

    void setAnimations(@AnimRes int enter, @AnimRes int exit);

    void setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit);
}
