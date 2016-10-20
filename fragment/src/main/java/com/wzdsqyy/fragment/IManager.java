package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;

/**
 * Created by Qiuyy on 2016/8/26.
 */
interface IManager<T> {
    T setAnimations(@AnimRes int enter, @AnimRes int exit);

    T setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit);
}
