package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.MutiItem;

/**
 * Created by Administrator on 2016/11/18.
 */
public class MutiItemHelper implements MutiItem {
    private int layoutRes = -1;

    int getMutiItemViewType() {
        return layoutRes;
    }

    void setMutiItemViewType(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    public MutiItemHelper getMutiItem() {
        return this;
    }

    boolean isBrotherType(@NonNull MutiItem other) {
        if (other.getMutiItem().getMutiItemViewType() == layoutRes && other.getMutiItem() != this) {
            return true;
        }
        return false;
    }
}
