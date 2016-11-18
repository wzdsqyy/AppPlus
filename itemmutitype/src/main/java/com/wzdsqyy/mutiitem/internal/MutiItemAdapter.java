package com.wzdsqyy.mutiitem.internal;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;

import java.util.List;

/**
 * 多种视图适配器
 */
public class MutiItemAdapter<M extends MutiItem> extends AbsMutiItemAdapter<M,List<M>> {
    public MutiItemAdapter(MutiItemBinderFactory factory) {
        super(factory);
    }
}
