package com.wzdsqyy.mutiitem;

import android.support.v7.widget.RecyclerView;

public interface MutiItemBinder<D>{
    void onBindViewHolder(D bean, int possion);
    void init(RecyclerView.ViewHolder holder, RecyclerView.Adapter adapter);
}
