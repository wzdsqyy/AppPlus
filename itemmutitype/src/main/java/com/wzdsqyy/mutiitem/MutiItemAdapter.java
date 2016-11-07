package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 多种视图适配器
 */

public class MutiItemAdapter<M extends MutiItemSuport> extends BaseRVAdapter<MutiItemHolder, M> {
    MutiItemBinderFactory factory;
    ArrayList<Class> clazzs;
    ArrayList<Integer> itemTypes;

    public MutiItemAdapter(MutiItemBinderFactory factory) {
        this.factory=factory;
        clazzs = new ArrayList<>();
        itemTypes = new ArrayList<>();
    }

    public MutiItemAdapter() {
        this(null);
    }

    public MutiItemAdapter setMutiItemBinderFactory(MutiItemBinderFactory factory) {
        this.factory = factory;
        return this;
    }

    /**
     * @param clazz     实体类型
     * @param layoutRes 对应的布局Id
     * @return
     */
    public MutiItemAdapter register(Class<? extends MutiItemSuport> clazz, @LayoutRes int layoutRes) {
        int index = clazzs.indexOf(clazz);
        if (index == -1) {
            clazzs.add(clazz);
            itemTypes.add(layoutRes);
        }
        return this;
    }


    /**
     * 将会替换你设置的布局管理器
     *
     * @param recyclerView
     * @param spanSize
     */
    public void diffSpanSizItem(@NonNull RecyclerView recyclerView, @NonNull SpanSize spanSize) {
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(), spanSize.getSpanCount());
        DiffSpanSize span = new DiffSpanSize(this, spanSize);
        manager.setSpanSizeLookup(span);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    public void setViewLayoutManager(@NonNull RecyclerView recyclerView){
        setViewLayoutManager(recyclerView,true);
    }

    public void setViewLayoutManager(@NonNull RecyclerView recyclerView,boolean isVertical) {
        LinearLayoutManager manager;
        if(isVertical){
            manager = new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.VERTICAL,false);
        }else {
            manager = new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        }
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    @Override
    public MutiItemHolder newViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        if(factory==null){
            throw new RuntimeException("必须提前设置 MutiItemBinderFactory");
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        MutiItemBinder mutiItemBinder = factory.getMutiItemHolder(viewType);
        MutiItemHolder holder = new MutiItemHolder(this, view, mutiItemBinder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MutiItemHolder holder, int position) {
        holder.getItemHolder().onBindViewHolder(holder, getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        MutiItemSuport item = getItem(position);
        int type = item.getMutiItemViewType();
        if (type > 0) {
            return type;
        }
        int index = clazzs.indexOf(item.getClass());
        if (index < 0 || index >= itemTypes.size()) {
            return super.getItemViewType(position);
        }
        type = itemTypes.get(index);
        item.setMutiItemViewType(type);
        return type;
    }
}
