package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemPayLoadBinder;

/**
 * Created by Administrator on 2016/10/13.
 */

class DefaultMutiItemHolder extends RecyclerView.ViewHolder {
    MutiItemBinder itemHolder;

    DefaultMutiItemHolder(@NonNull View itemView) {
        super(itemView);
    }

    public DefaultMutiItemHolder setMutiItemBinder(MutiItemBinder itemHolder) {
        this.itemHolder = itemHolder;
        return this;
    }

    public MutiItemPayLoadBinder getMutiItemPayLoadBinder() {
        return itemHolder instanceof MutiItemPayLoadBinder? (MutiItemPayLoadBinder) itemHolder :null;
    }

    public MutiItemBinder getMutiItemBinder() {
        return itemHolder;
    }
}
