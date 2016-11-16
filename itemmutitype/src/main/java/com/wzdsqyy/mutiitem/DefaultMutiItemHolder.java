package com.wzdsqyy.mutiitem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/10/13.
 */

class DefaultMutiItemHolder extends RecyclerView.ViewHolder {
    MutiItemBinder itemHolder;

    DefaultMutiItemHolder(@NonNull View itemView) {
        super(itemView);
    }

    public DefaultMutiItemHolder setItemHolder(MutiItemBinder itemHolder) {
        this.itemHolder = itemHolder;
        return this;
    }

    public MutiItemBinder getMutiItemBinder() {
        return itemHolder;
    }
}
