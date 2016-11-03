package com.wzdsqyy.commonview.refresh;

/**
 * 回调接口
 */
public interface OnFreshListener {
    /**
     * 下拉刷新，回调接口
     */
    void onRefresh();
    /**
     * 上拉加载，回调接口
     */
    void onLoadmore();
}
