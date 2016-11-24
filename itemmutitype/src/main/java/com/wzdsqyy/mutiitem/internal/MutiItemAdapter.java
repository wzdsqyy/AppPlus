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
import com.wzdsqyy.mutiitem.MutiItemPayLoadBinder;
import com.wzdsqyy.mutiitem.SpanSize;

import java.util.ArrayList;
import java.util.List;

/**
 * 多种视图适配器
 */
public class MutiItemAdapter extends BaseRVAdapter<RecyclerView.ViewHolder,MutiItem> {
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
    public MutiItemAdapter register(Class<MutiItem> clazz, @LayoutRes int layoutRes) {
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
        binder.onBindViewHolder(getItem(position),position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }else {
            boolean isBinder = holder instanceof DefaultMutiItemHolder;
            MutiItemBinder binder = isBinder ? ((DefaultMutiItemHolder) holder).getMutiItemBinder() : (MutiItemBinder) holder;
            if(binder instanceof MutiItemPayLoadBinder){
                ((MutiItemPayLoadBinder) binder).onBindViewHolder(position,payloads.get(0));
            }else {
                binder.onBindViewHolder(getItem(position),position);
            }
        }
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
