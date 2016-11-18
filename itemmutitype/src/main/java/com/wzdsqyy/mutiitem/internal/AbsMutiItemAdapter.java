package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.SpanSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

class AbsMutiItemAdapter<M extends MutiItem,L extends List<M>> extends BaseRVAdapter<RecyclerView.ViewHolder, M,L> {
    private MutiItemBinderFactory factory;
    private ArrayList<Class> clazzs;
    private ArrayList<Integer> itemTypes;

    public AbsMutiItemAdapter(MutiItemBinderFactory factory) {
        this.factory = factory;
        clazzs = new ArrayList<>();
        itemTypes = new ArrayList<>();
    }

    public AbsMutiItemAdapter setMutiItemBinderFactory(MutiItemBinderFactory factory) {
        this.factory = factory;
        return this;
    }

    /**
     * @param clazz     实体类型
     * @param layoutRes 对应的布局Id
     * @return
     */
    public AbsMutiItemAdapter register(Class<? extends MutiItem> clazz, @LayoutRes int layoutRes) {
        int index = clazzs.indexOf(clazz);
        if (index == -1) {
            clazzs.add(clazz);
            itemTypes.add(layoutRes);
        }
        return this;
    }

    public boolean isRegister(@LayoutRes int layoutRes) {
        return itemTypes.indexOf(layoutRes) != -1;
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
            holder = new DefaultMutiItemHolder(view).setMutiItemBinder(mutiItemBinder);
        }
        mutiItemBinder.init(holder, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean isBinder = holder instanceof DefaultMutiItemHolder;
        MutiItemBinder binder = isBinder ? ((DefaultMutiItemHolder) holder).getMutiItemBinder() : (MutiItemBinder) holder;
        binder.onBindViewHolder(getItem(position), position);
    }

    public int getItemViewType(MutiItem item) {
        int type = item.getMutiItem().getMutiItemViewType();
        if (type > 0) {
            return type;
        }
        int index = clazzs.indexOf(item.getClass());
        if (index != -1) {
            type = itemTypes.get(index);
            item.getMutiItem().setMutiItemViewType(type);
            return type;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(getItem(position));
    }
}