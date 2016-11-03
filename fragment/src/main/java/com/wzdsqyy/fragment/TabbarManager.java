package com.wzdsqyy.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface TabbarManager extends Manager {
    /**
     * 显示页面
     *
     * @param index 索引，第几个添加的
     */
    TabbarManager showPage(int index);


    /**
     * 显示页面
     *
     * @param index 索引，第几个添加的
     */
    TabbarManager showPage(int index,boolean anim);


    SaveState getSaveState();

    /**
     * 添加页面
     */
    <F extends Fragment> TabbarManager addPage(F page, String tag);

    /**
     * 添加页面
     */
    <F extends Fragment> TabbarManager addPage(F page);


    /**
     * 默认显示第一个add的page
     *
     * @return
     */
    TabbarManager commit();
}
