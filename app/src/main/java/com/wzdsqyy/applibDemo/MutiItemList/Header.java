package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemHolder;

/**
 * Created by Administrator on 2016/10/21.
 */

public class Header extends RecyclerView.OnScrollListener implements MutiItemBinder<StickyModel> {
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    public Header(RecyclerView recyclerView) {
        this.manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(MutiItemHolder holder, StickyModel bean, int possion) {
        RecyclerView view;
//        view.setOverScrollMode();

//        view.offsetChildrenVertical();




















    }

    @Override
    public void init(MutiItemHolder holder) {
        recyclerView.addOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
