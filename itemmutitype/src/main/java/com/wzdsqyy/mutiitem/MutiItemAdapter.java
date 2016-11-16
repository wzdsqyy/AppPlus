package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 多种视图适配器
 */

public class MutiItemAdapter<M extends MutiItemSuport> extends BaseRVAdapter<RecyclerView.ViewHolder, M> {
    private MutiItemBinderFactory factory;
    private ArrayList<Class> clazzs;
    private ArrayList<Integer> itemTypes;

    public MutiItemAdapter(MutiItemBinderFactory factory) {
        this.factory = factory;
        clazzs = new ArrayList<>();
        itemTypes = new ArrayList<>();
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

    public boolean isRegister(@LayoutRes int layoutRes){
        return itemTypes.indexOf(layoutRes)!=-1;
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

    @Override
    public RecyclerView.ViewHolder newViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        if (factory == null) {
            throw new RuntimeException("必须提前设置 MutiItemBinderFactory");
        }
        MutiItemBinder mutiItemBinder = factory.getMutiItemBinder(viewType, parent);
        RecyclerView.ViewHolder holder;
        if (mutiItemBinder instanceof RecyclerView.ViewHolder) {
            holder = (RecyclerView.ViewHolder) mutiItemBinder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            holder = new DefaultMutiItemHolder(view).setItemHolder(mutiItemBinder);
        }
        mutiItemBinder.init(holder, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DefaultMutiItemHolder) {
            ((DefaultMutiItemHolder) holder).getMutiItemBinder().onBindViewHolder(getItem(position), position);
        } else {
            ((MutiItemBinder) holder).onBindViewHolder(getItem(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        MutiItemSuport item = getItem(position);
        int type = item.getMutiItemViewType();
        if (type > 0) {
            return type;
        }
        int index = clazzs.indexOf(item.getClass());
        if (index != -1) {
            type = itemTypes.get(index);
            item.setMutiItemViewType(type);
            return type;
        }
        return super.getItemViewType(position);
    }
}
