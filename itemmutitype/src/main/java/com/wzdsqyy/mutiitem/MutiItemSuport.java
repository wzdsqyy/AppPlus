package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;

/**
 * 实体类型对应的视图
 */

public interface MutiItemSuport {
    /**
     * 负数表示没有对应的布局
     *
     * @return
     */
    @LayoutRes
    int getMutiItemViewType();

    /**
     * 缓存
     *
     * @param viewType
     */
    void setMutiItemViewType(@LayoutRes int viewType);
}
