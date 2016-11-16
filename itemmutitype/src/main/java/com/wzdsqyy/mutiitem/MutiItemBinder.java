package com.wzdsqyy.mutiitem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public interface MutiItemBinder<D>{
    void onBindViewHolder(D bean, int possion);
    void init(@NonNull RecyclerView.ViewHolder holder,@NonNull RecyclerView.Adapter adapter);
}
