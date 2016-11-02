package com.wzdsqyy.fragment;


import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface NavManager {
    /**
     * 显示一个页面并加入返回栈
     *
     * @param page
     * @param tag
     */
    NavManager pushPage(Fragment page, String tag);

    /**
     * 顶层页面出栈
     */
    NavManager popPage();

    /**
     * 清空栈
     */
    NavManager clearNav();

    /**
     * 显示一个页面不加入返回栈
     *
     * @param page
     * @param tag
     */
    NavManager showPage(Fragment page, String tag);

    NavManager showPage(Fragment page, String tag, boolean isAnim);

    /**
     * 点击返回按钮
     *
     * @return
     */
    boolean onBackPressed();

    int getBackStackCount();

    void setAnimations(@AnimRes int enter, @AnimRes int exit);

    void setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit);
}
