package com.wzdsqyy.applib.ui.mutiitem;

public interface MutiItemHolder<D>{
    void onBindViewHolder(MutiHolder holder,D bean,int possion);
    void init(MutiHolder holder);
}
