package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/12.
 */

class DiffSpanSize extends GridLayoutManager.SpanSizeLookup {
    MutiItemAdapter adapter;
    private int maxCount=1;//记录一行里最大的
    private ArrayList<Integer> counts = new ArrayList<>();
    private SparseIntArray mCounts = new SparseIntArray();
    private boolean calculated=false;
    private int spanCount=1;

    DiffSpanSize(@NonNull MutiItemAdapter adapter) {
        this.adapter = adapter;
        setSpanIndexCacheEnabled(true);
    }
    /**
     * 指定布局每一行有多少个
     *
     * @param layoutRes
     * @param count
     */
    void addItemCount(@LayoutRes int layoutRes, @IntRange(from = 1,to = 5) int count) {
        if (maxCount < count) {
            maxCount = count;
        }
        int index = counts.indexOf(count);
        if (index < 0) {
            counts.add(count);
        }
        mCounts.put(layoutRes, count);
        calculated=false;
    }

    void setLayoutManager(RecyclerView recyclerView) {
        setLayoutManager(recyclerView, RecyclerView.VERTICAL);
    }

    GridLayoutManager setLayoutManager(RecyclerView recyclerView, int orientation) {
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(), getSpanCount(), orientation,false);
        manager.setSpanSizeLookup(this);
        setSpanIndexCacheEnabled(true);
        recyclerView.setLayoutManager(manager);
        return manager;
    }

    @Override
    public int getSpanSize(int position) {
        return spanCount/(mCounts.get(adapter.getItemViewType(position),1));
    }

    int getSpanCount() {
        if(calculated){
            return spanCount;
        }
        int result;
        for (int i = 0; i < counts.size(); i++) {
            result=maxCount%counts.get(i);
            if(result!=0){
                maxCount=maxCount*counts.get(i);
            }
        }
        spanCount=maxCount;
        calculated=true;
        return spanCount;
    }
}
