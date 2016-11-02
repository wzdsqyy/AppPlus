package com.wzdsqyy.fragment;

import android.support.annotation.AnimRes;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface TabbarManager {
    /**
     * 显示页面
     *
     * @param index 索引，第几个添加的
     */
    TabbarManager showPage(int index);


    SaveState getSaveState();

    /**
     * 添加页面
     */
    TabbarManager addPage(BaseFragment page, String tag);

    /**
     * 添加页面
     */
    TabbarManager addPage(BaseFragment page);


    /**
     * 默认显示第一个add的page
     *
     * @return
     */
    TabbarManager commit();

    /**
     * 点击返回按钮
     *
     * @return
     */
    boolean onBackPressed();

    void setAnimations(@AnimRes int enter, @AnimRes int exit);

    void setAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit);
}
