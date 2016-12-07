package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

/**
 * Created by Quinta on 2016/12/7.
 */
class NarmalPolicy extends RecyclerView.AdapterDataObserver implements ItemType{
    private SparseIntArray caches=new SparseIntArray();
    private MutiItemAdapter adapter;

    public NarmalPolicy(@NonNull MutiItemAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        caches.clear();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        caches.clear();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        caches.clear();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        caches.clear();
    }


    @Override
    public int getItemViewType(int position) {
        int type = caches.get(position, -1);
        if (type != -1) {
            return type;
        }
        type = adapter.getItemType(position);
        if(type==-1) {
            return 0;
        }
        caches.put(position,type);
        return type;
    }

        @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        caches.clear();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        caches.clear();
    }
}
