package com.wzdsqyy.applib.ui.sticky;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2016/10/19.
 */

interface OnScrollHandler {
    RecyclerView.ViewHolder getStickyHolder();
    void setStickyHolder(RecyclerView.ViewHolder holder);
    boolean isStickyItem(int type);
}
