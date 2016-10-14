package com.wzdsqyy.mutiitem;

public interface MutiItemBinder<D>{
    void onBindViewHolder(MutiHolder holder,D bean,int possion);
    void init(MutiHolder holder);
}
