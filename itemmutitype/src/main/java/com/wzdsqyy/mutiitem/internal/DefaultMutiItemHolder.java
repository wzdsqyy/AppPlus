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

    DefaultMutiItemHolder setMutiItemBinder(MutiItemBinder itemHolder) {
        this.itemHolder = itemHolder;
        return this;
    }

    PayLoadBinder getPayLoadBinder() {
        return payLoadBinder;
    }

    DefaultMutiItemHolder setPayLoadBinder(PayLoadBinder payLoadBinder) {
        this.payLoadBinder = payLoadBinder;
        return this;
    }

    public MutiItemBinder getMutiItemBinder() {
        return itemHolder;
    }
}
