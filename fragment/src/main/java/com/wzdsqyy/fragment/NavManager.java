package com.wzdsqyy.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface NavManager extends Manager {
    /**
     * 显示一个页面并加入返回栈
     *
     * @param page
     * @param tag
     */
    <F extends Fragment> NavManager pushPage(F page, String tag);

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
    <F extends Fragment> NavManager showPage(F page, String tag);

    <F extends Fragment> NavManager showPage(F page, String tag, boolean isAnim);

    int getBackStackCount();
}
