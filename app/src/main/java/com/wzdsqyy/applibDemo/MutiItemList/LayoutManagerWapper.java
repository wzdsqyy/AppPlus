package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class LayoutManagerWapper extends RecyclerView.LayoutManager {
    private static final String TAG = "LayoutManagerWapper";
    RecyclerView.LayoutManager manager;

    public LayoutManagerWapper(RecyclerView.LayoutManager manager) {
        this.manager = manager;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "scrollVerticallyBy() called with: dy = [" + dy + "], recycler = [" + recycler + "], state = [" + state + "]");
        return manager.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        super.onLayoutChildren(recycler,state);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return manager.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return manager.generateDefaultLayoutParams();
    }
}
