package com.wzdsqyy.applibDemo.MutiItemList;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.internal.MutiItemHelper;

/**
 * Created by Administrator on 2016/10/18.
 */

public class StickyModel2 implements MutiItem {

    private String name;

    public StickyModel2(String name) {
        this.name = name;
    }

    private int type = -1;


    @Override
    public String toString() {
        return name;
    }

    private MutiItemHelper helper = new MutiItemHelper();

    @Override
    public MutiItemHelper getMutiItem() {
        return helper;
    }
}
