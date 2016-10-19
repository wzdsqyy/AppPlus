package com.wzdsqyy.mutiitem;

public interface MutiItemBinder<D>{
    void onBindViewHolder(MutiItemHolder holder, D bean, int possion);
    void init(MutiItemHolder holder);
}
