package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface TabbarManager{
    /**
     * 显示页面
     *
     * @param page
     */
    void showPage(BaseFragment page);

    /**
     * 隐藏页面
     *
     * @param page
     */
    void hidePage(BaseFragment page);

    /**
     * 添加页面
     */
    void addPage(BaseFragment page, String tag);

    /**
     * 添加页面
     */
    void addPage(BaseFragment page);

    /**
     * 点击返回按钮
     *
     * @return
     */
    boolean onBackPressed();

    /**
     * @return Optional identifier of the container this fragment is
     * to be placed in.  If 0, it will not be placed in a container.
     */
    @IdRes
    int getTabContentId();


    FragmentManager getTabbarFragmentManager();

    TabbarManager setAnimations(@AnimRes int enter, @AnimRes int exit);

    TabbarManager setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit);
}
