package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.MutiItemSuport;

/**
 * Created by Administrator on 2016/10/12.
 */

public class Teacher implements MutiItemSuport {
    private int mItemType=-1;
    @Override
    public int getMutiItemViewType() {
        return mItemType;
    }

    @Override
    public void setMutiItemViewType(@LayoutRes int type) {
        this.mItemType=type;
    }
}
