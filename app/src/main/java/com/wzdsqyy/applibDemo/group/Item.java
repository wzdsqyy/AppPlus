package com.wzdsqyy.applibDemo.group;

import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.MutiItemSuport;

/**
 * Created by Administrator on 2016/11/16.
 */

public class Item implements MutiItemSuport {
    private int layoutRes = -1;
    private String item = "组员_";

    public Item(int possion) {
        this.item = this.item + possion+"--";
    }

    @Override
    public int getMutiItemViewType() {
        return layoutRes;
    }

    public String getItem() {
        return item;
    }

    @Override
    public void setMutiItemViewType(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }
}
