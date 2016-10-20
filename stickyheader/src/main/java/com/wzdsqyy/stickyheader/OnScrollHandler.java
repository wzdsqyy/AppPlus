package com.wzdsqyy.stickyheader;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2016/10/19.
 */

interface OnScrollHandler {
    @NonNull
    RecyclerView.ViewHolder getStickyHolder(@LayoutRes int viewType);

    boolean isStickyItem(int type);

    boolean hasStickyItem();

    void showStickyHolder(int type, boolean show);

    void setStickyTranslationY(@LayoutRes int viewType, int y);
}
