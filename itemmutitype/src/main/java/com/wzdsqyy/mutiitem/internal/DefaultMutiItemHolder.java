package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.PayLoadBinder;

/**
 * Created by Administrator on 2016/10/13.
 */

class DefaultMutiItemHolder extends RecyclerView.ViewHolder implements MutiItemBinder,PayLoadBinder{
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

    public MutiItemBinder getMutiItemBinder() {
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(Object bean, int possion) {
        itemHolder.onBindViewHolder(bean,possion);
    }

    @Override
    public void init(@NonNull RecyclerView.ViewHolder holder, @NonNull MutiItemAdapter adapter) {
        itemHolder.init(holder,adapter);
    }

    @Override
    public void onBindViewHolder(int possion, Object payload) {
        if(payLoadBinder!=null){
            payLoadBinder.onBindViewHolder(possion,payload);
        }
    }
}
