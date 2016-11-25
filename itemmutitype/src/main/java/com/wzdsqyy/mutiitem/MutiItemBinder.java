package com.wzdsqyy.mutiitem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wzdsqyy.mutiitem.internal.MutiItemAdapter;

public interface MutiItemBinder<D>{
    void onBindViewHolder(D bean, int possion);
    void init(@NonNull RecyclerView.ViewHolder holder,@NonNull MutiItemAdapter adapter);
}
