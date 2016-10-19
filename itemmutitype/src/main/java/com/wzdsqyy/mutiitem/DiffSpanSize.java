package com.wzdsqyy.mutiitem;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Administrator on 2016/10/12.
 */

class DiffSpanSize extends GridLayoutManager.SpanSizeLookup {
    MutiItemAdapter adapter;
    SpanSize spanSize;

    public DiffSpanSize(@NonNull MutiItemAdapter adapter, @NonNull SpanSize spanSize) {
        this.adapter = adapter;
        this.spanSize = spanSize;
        setSpanIndexCacheEnabled(true);
    }

    @Override
    public int getSpanSize(int position) {
        int type = adapter.getItemViewType(position);
        int size = spanSize.getSpanSize(type);
        if (size < 0 || size > spanSize.getSpanCount()) {
            return 1;
        }else {
            return size;
        }
    }
}
