package com.wzdsqyy.mutiitem;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/10/13.
 */

public class MutiItemHolder extends RecyclerView.ViewHolder {
    MutiItemBinder itemHolder;
    MutiItemAdapter adapter;

    MutiItemHolder(MutiItemAdapter adapter, @NonNull View itemView, @NonNull MutiItemBinder mutiItemBinder) {
        super(itemView);
        this.itemHolder = mutiItemBinder;
        this.adapter = adapter;
        if (this.itemHolder == null) {
            return;
        }
        this.itemHolder.init(this);
    }

    public View findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public MutiItemBinder getItemHolder() {
        return itemHolder;
    }

    public MutiItemAdapter getMutiAdapter() {
        return adapter;
    }
}
