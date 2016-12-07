package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.MutiItem;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MutiItemHelper implements MutiItem{
    int layoutRes = -1;
    @Override
    public MutiItemHelper getMutiItem() {
        return this;
    }

    boolean isBrotherType(@NonNull MutiItem other) {
        if (other.getMutiItem().layoutRes== layoutRes && other.getMutiItem() != this) {
            return true;
        }
        return false;
    }
}
