package com.wzdsqyy.applib.ui.sticky;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/10/18.
 */

class StickyItemListener extends RecyclerView.OnScrollListener implements View.OnTouchListener {
    OnScrollHandler scrollListener;
    LinearLayoutManager manager;
    RecyclerView.Adapter adapter;
    boolean scroll=false;
    int stickyItemViewType = -1;
    int previousPosition = -1;

    public StickyItemListener(RecyclerView view) {
        this.adapter = view.getAdapter();
        if (view.getLayoutManager() instanceof LinearLayoutManager)
            this.manager = (LinearLayoutManager) view.getLayoutManager();
        view.addOnScrollListener(this);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (stickyItemViewType == -1) {
            return;
        }
        if (manager == null) {
            return;
        }
        if (adapter == null) {
            return;
        }
        if (scrollListener == null || scrollListener.getStickyHolder() == null) {
            return;
        }
        int position = manager.findFirstVisibleItemPosition();
        if(stickyItemViewType==adapter.getItemViewType(position+1)){
            View view = manager.findViewByPosition(position+1);
            int mydx = view.getTop() - scrollListener.getStickyHolder().itemView.getHeight();
            if(mydx<0){
                scrollListener.getStickyHolder().itemView.setTranslationY(mydx);
            }else {

            }
            if(Math.abs(mydx)>=scrollListener.getStickyHolder().itemView.getHeight()){
                scrollListener.getStickyHolder().itemView.setTranslationY(0);
                stickyNewViewHolder(position+1);
            }else{


            }
        }






        if (position + 1 < adapter.getItemCount()) {
            int type = adapter.getItemViewType(position + 1);
            View nextTitleView = manager.findViewByPosition(position + 1);
            if (type == stickyItemViewType) {
                if (nextTitleView.getTop() <= scrollListener.getStickyHolder().itemView.getHeight()) {
                    scrollListener.getStickyHolder().itemView.setTranslationY(nextTitleView.getTop() - scrollListener.getStickyHolder().itemView.getHeight());
                }
                // hide -> show
                stickyNewViewHolder(position + 1);
            } else if (scrollListener.getStickyHolder().itemView.getTranslationY() != 0) {
                scrollListener.getStickyHolder().itemView.setTranslationY(0);
            }
        }

        if (stickyItemViewType == adapter.getItemViewType(position)) {
            stickyNewViewHolder(position);
        } else if (dy < 0 && scrollListener.getStickyHolder().itemView.getVisibility() != VISIBLE) {
            View nextTitleView = manager.findViewByPosition(position);
            if (nextTitleView.getBottom() >= scrollListener.getStickyHolder().itemView.getHeight()) {
                stickyNewViewHolder(position);
            }
        }
    }

    private void stickyNewViewHolder(int position) {
        adapter.onBindViewHolder(scrollListener.getStickyHolder(), position);
    }

    private void preViewHolder(int position) {
        while (adapter.getItemViewType(position) != stickyItemViewType && position > 0) {
            position--;
        }
        adapter.onBindViewHolder(scrollListener.getStickyHolder(), position);
    }

    private void nextViewHolder(int position) {
        int viewType = 0;
        while (adapter.getItemViewType(position) != viewType && position < adapter.getItemCount()) {
            position++;
        }
        adapter.onBindViewHolder(scrollListener.getStickyHolder(), position);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
