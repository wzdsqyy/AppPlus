package com.wzdsqyy.applib.ui.fragment;

/**
 * Created by Qiuyy on 2016/8/25.
 */
public interface TabbarManager extends Tabbar,IManager<TabbarManager> {
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
}
