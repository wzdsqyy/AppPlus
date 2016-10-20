package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.MutiItemSuport;

/**
 * Created by Administrator on 2016/10/18.
 */

public class StickyModel implements MutiItemSuport {

    private String name;

    public StickyModel(String name) {
        this.name = name;
    }

    private int type=-1;
    @Override
    public int getMutiItemViewType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setMutiItemViewType(@LayoutRes int layoutRes) {
        this.type=layoutRes;
    }
}
