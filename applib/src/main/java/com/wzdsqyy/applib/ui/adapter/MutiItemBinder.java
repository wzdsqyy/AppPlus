package com.wzdsqyy.applib.ui.adapter;

public interface MutiItemBinder<D>{
    void onBindViewHolder(MutiHolder holder,D bean,int possion);
    void init(MutiHolder holder);
}
