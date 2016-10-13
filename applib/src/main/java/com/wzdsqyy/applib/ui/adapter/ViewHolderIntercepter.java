package com.wzdsqyy.applib.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2016/10/13.
 */

public interface ViewHolderIntercepter<VH extends RecyclerView.ViewHolder> {
    void afterIntercepter(VH holder,@LayoutRes int viewType);
}
