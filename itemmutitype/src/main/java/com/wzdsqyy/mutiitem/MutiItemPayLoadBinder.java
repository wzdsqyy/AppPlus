package com.wzdsqyy.mutiitem;

public interface MutiItemPayLoadBinder<D> extends MutiItemBinder<D>{
    void onBindViewHolder(int possion,Object payload);
}
