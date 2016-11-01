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
     * 显示一个页面并加入返回栈
     *
     * @param page
     */
    NavManager pushPage(Fragment page);

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

    /**
     * @return Optional identifier of the container this fragment is
     * to be placed in.  If 0, it will not be placed in a container.
     */
    int getNavContentId();

    Fragment getRootFragment();

    NavManager setAnimations(@AnimRes int enter, @AnimRes int exit);

    NavManager setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit);
}
