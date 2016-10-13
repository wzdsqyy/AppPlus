package com.wzdsqyy.applib.ui.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/10/13.
 */

public class MutiHolder<H> extends RecyclerView.ViewHolder {
    private MutiItemBinder itemHolder;
    private MutiAdapter adapter;

    MutiHolder(MutiAdapter adapter, @NonNull View itemView, @NonNull MutiItemBinder mutiItemBinder) {
        super(itemView);
        this.itemHolder = mutiItemBinder;
        this.adapter = adapter;
        this.itemHolder.init(this);
    }

    public View findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public MutiItemBinder getItemHolder() {
        return itemHolder;
    }

    public MutiAdapter getMutiAdapter() {
        return adapter;
    }
}
