package com.wzdsqyy.applib.ui.sticky;

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

    void setStickyTranslationY(@LayoutRes int viewType,int y);
}
