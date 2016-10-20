package com.wzdsqyy.applib.ui.sticky;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/10/18.
 */

class StickyItemListener extends RecyclerView.OnScrollListener {
    OnScrollHandler scrollListener;
    LinearLayoutManager manager;
    RecyclerView.Adapter adapter;
    boolean scroll = true;

    public StickyItemListener(OnScrollHandler scrollListener, @NonNull RecyclerView view) {
        this.scrollListener = scrollListener;
        this.adapter = view.getAdapter();
        if (view.getLayoutManager() instanceof LinearLayoutManager)
            this.manager = (LinearLayoutManager) view.getLayoutManager();
        view.addOnScrollListener(this);
    }

    boolean isScroll() {
        return scroll;
    }

    StickyItemListener setScroll(boolean scroll) {
        this.scroll = scroll;
        return this;
    }

    public StickyItemListener(@NonNull RecyclerView view) {
        this(null, view);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (manager == null) {
            return;
        }
        if (adapter == null) {
            return;
        }
        if (scrollListener == null) {
            return;
        }
        int targetPosition = manager.findFirstVisibleItemPosition();
        int end = manager.findLastVisibleItemPosition();
        targetPosition = getStickyPosition(targetPosition, end);
        int type = adapter.getItemViewType(targetPosition);
        if (!isStickyType(type)) {
            return;//不是stick的类型直接返回
        }
        View view = manager.findViewByPosition(targetPosition);//找到对应的View
        int distance = view.getTop() - scrollListener.getStickyHolder(type).itemView.getHeight();
        if (distance > 0) {
            if (isScroll()) {
                distance=0;
            }
            showPrePosition(targetPosition);
        } else {
            if (view.getTop() < 0) {
                if(isScroll()){
                    distance=0;
                }
                showPosition(targetPosition);
            } else {
                showPrePosition(targetPosition);
            }
        }
        scrollListener.setStickyTranslationY(type,distance);
    }

    /**
     * @param start 起始
     * @param end   结束
     * @return 如果有则返回 最靠近 start 的Position，否则直接返回start的值
     */
    private int getStickyPosition(int start, int end) {
        while (start < end && (!isStickyPosition(start))) {
            start = start + 1;
        }
        return start;
    }

    /**
     * 初始化黏性头部的位置
     *
     * @param position
     * @return
     */
    private void initStickyViewHolder(int position) {
        int type = adapter.getItemViewType(position);
        if (!scrollListener.isStickyItem(type)) {//不是sticky类型直接返回
            return;
        }
        showPosition(position);//显示数据
    }

    /**
     * 显示当前黏性头部数据的位置
     *
     * @param position
     * @return
     */
    private void showPosition(int position) {
        if (position < 0 || position > adapter.getItemCount()) {
            return;
        }
        int type = adapter.getItemViewType(position);
        scrollListener.getStickyHolder(type).itemView.setVisibility(View.VISIBLE);
        adapter.onBindViewHolder(scrollListener.getStickyHolder(type), position);
    }


    /**
     * 是否是黏性位置的position
     *
     * @param position
     * @return
     */
    private boolean isStickyPosition(int position) {
        return isStickyType(adapter.getItemViewType(position));
    }

    /**
     * 是否是黏性位置的position
     *
     * @param viewtype
     * @return
     */
    private boolean isStickyType(@LayoutRes int viewtype) {
        return scrollListener.isStickyItem(viewtype);
    }

    /**
     * 显示当前黏性头部数据的位置
     *
     * @param position
     * @return
     */
    private void showPrePosition(int position) {
        int prePosition = position - 1;
        while (prePosition >= 0 && (!isStickyPosition(prePosition))) {
            prePosition--;
        }
        if (prePosition < position - 1) {
            showPosition(prePosition);
        }
    }
}
