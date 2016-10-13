package com.wzdsqyy.applib.ui.mutiitem;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/10/13.
 */

public class MutiHolder<H> extends RecyclerView.ViewHolder {
    private MutiItemHolder itemHolder;
    private MutiAdapter<H> adapter;

    public MutiHolder(MutiAdapter<H> adapter, @NonNull View itemView, @NonNull MutiItemHolder mutiItemHolder) {
        super(itemView);
        this.itemHolder = mutiItemHolder;
        this.adapter = adapter;
        this.itemHolder.init(this);
    }

    public View findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public MutiItemHolder getItemHolder() {
        return itemHolder;
    }

    public MutiAdapter<H> getMutiAdapter() {
        return adapter;
    }
}
