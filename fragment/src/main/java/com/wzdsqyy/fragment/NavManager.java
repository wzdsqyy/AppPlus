package com.wzdsqyy.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface NavManager extends Nav, IManager<NavManager> {
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
     * 当前是否已经是栈底
     */
    boolean isRootPage();

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

    FragmentManager getNavFragmentManager();

    NavManager setOnNavBackStackListener(OnNavBackStackListener listener);
}
