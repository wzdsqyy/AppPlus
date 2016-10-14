package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;

/**
 * 实体类型对应的视图
 */

public interface ItemTypeSuport {
    /**
     * 负数表示没有对应的布局
     *
     * @return
     */
    @LayoutRes
    int getItemType();

    /**
     * 缓存
     *
     * @param layoutRes
     */
    void setItemType(@LayoutRes int layoutRes);
}
