package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.PayLoadBinder;

/**
 * Created by Administrator on 2016/10/13.
 */

class DefaultMutiItemHolder extends RecyclerView.ViewHolder {
    MutiItemBinder itemHolder;
    PayLoadBinder payLoadBinder;

    DefaultMutiItemHolder(@NonNull View itemView) {
        super(itemView);
    }

    DefaultMutiItemHolder setBinder(MutiItemBinder itemHolder) {
        this.itemHolder = itemHolder;
        if (itemHolder instanceof PayLoadBinder) {
            payLoadBinder = (PayLoadBinder) itemHolder;
        }
        return this;
    }

    PayLoadBinder getPayLoadBinder() {
        return payLoadBinder;
    }

    public MutiItemBinder getMutiItemBinder() {
        return itemHolder;
    }
}
