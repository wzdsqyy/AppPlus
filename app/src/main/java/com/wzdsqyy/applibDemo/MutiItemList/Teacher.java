package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.ItemTypeSuport;

/**
 * Created by Administrator on 2016/10/12.
 */

public class Teacher implements ItemTypeSuport {
    private int mItemType=-1;
    @Override
    public int getItemType() {
        return mItemType;
    }

    @Override
    public void setItemType(@LayoutRes int type) {
        this.mItemType=type;
    }
}
