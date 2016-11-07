package com.wzdsqyy.commonview.refresh;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface UIHandler {
    View getView(ViewGroup parent);
    /**
     *
     * @param view
     * @param dy
     * @param distance
     * @param isTouch
     * @return
     */
    boolean onMove(View view, float dy, int distance,boolean isTouch);


    float moveView(int distance);

    /**
     * @return true下拉刷新的header，false 上拉加载的footer
     */
    boolean isHeader();
}
