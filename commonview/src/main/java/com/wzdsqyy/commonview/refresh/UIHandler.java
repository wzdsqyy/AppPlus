package com.wzdsqyy.commonview.refresh;

import android.view.View;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface UIHandler {

    int getDragLimitHeight(View rootView);

    int getDragMaxHeight(View rootView);

    int getDragSpringHeight(View rootView);

    void onPreDrag(View rootView);

    /**
     * @param dy
     */
    void onMove(View view, int dy);

    /**
     *
     * @param isUp 是否上拉
     */
    void onLimitDes(View rootView, boolean isUp);

    /**
     * 拉动超过临界点后松开时回调
     */
    void onStartAnim();

    /**
     * 头(尾)已经全部弹回时回调
     */
    void onFinishAnim();

    /**
     * @return true下拉刷新的header，false 上拉加载的footer
     */
    boolean isHeader();
}
