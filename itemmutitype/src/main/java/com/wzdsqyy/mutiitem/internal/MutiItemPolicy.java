package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;

import com.wzdsqyy.mutiitem.MutiItem;

/**
 * Created by Quinta on 2016/12/7.
 */
class MutiItemPolicy implements ItemType {
    private MutiItemAdapter<MutiItem> adapter;

    public MutiItemPolicy(@NonNull MutiItemAdapter<MutiItem> adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        MutiItemHelper itemHelper = adapter.getItem(position).getMutiItem();
        int type = itemHelper.layoutRes;
        if(type>0){
            return type;
        }
        type=adapter.getItemType(position);
        if(type>0){
            itemHelper.layoutRes=type;
            return type;
        }
        return 0;
    }
}
