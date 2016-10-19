package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2016/10/13.
 */

public interface MutiItemBinderIntercepter<VH extends RecyclerView.ViewHolder> {
    void afterIntercepter(VH holder,@LayoutRes int viewType);
}
