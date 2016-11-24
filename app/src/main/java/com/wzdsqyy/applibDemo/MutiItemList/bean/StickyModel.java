package com.wzdsqyy.applibDemo.MutiItemList.bean;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.internal.MutiItemHelper;

/**
 * Created by Administrator on 2016/10/18.
 */

public class StickyModel implements MutiItem {

    private String name;

    public StickyModel(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }


    private MutiItemHelper helper=new MutiItemHelper();
    @Override
    public MutiItemHelper getMutiItem() {
        return helper;
    }
}
