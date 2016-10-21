package com.wzdsqyy.commonview;

/**
 * Created by Administrator on 2016/10/20.
 */

public interface OnIndexTouchListener {
    void onIndexTouch(IndexBar view, boolean isTouch, int select);
    void onIndexScroll(IndexBar view,int index);
}
