package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.mutiitem.MutiItem;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.PayLoadBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * 多种视图适配器
 */
public class MutiItemAdapter<T extends MutiItem> extends BaseRVAdapter<RecyclerView.ViewHolder, T> {
    private MutiItemBinderFactory factory;
    private ArrayList<Class> clazzs = new ArrayList<>();
    private ArrayList<Integer> itemTypes = new ArrayList<>();
    private DiffSpanSize spanSize;
    private boolean isAttached=false;
    public MutiItemAdapter(MutiItemBinderFactory factory) {
        this.factory = factory;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MutiItemBinder mutiItemBinder = getMutiItemBinder(parent, viewType);
        RecyclerView.ViewHolder holder;
        if (mutiItemBinder instanceof RecyclerView.ViewHolder) {
            holder = (RecyclerView.ViewHolder) mutiItemBinder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            holder = new DefaultMutiItemHolder(view).setBinder(mutiItemBinder);
            mutiItemBinder= (MutiItemBinder) holder;
        }
        mutiItemBinder.init(holder, this);
        return holder;
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
    public MutiItemAdapter register(Class<T> clazz, @LayoutRes int layoutRes) {
        int index = clazzs.indexOf(clazz);
        if (index == -1) {
            clazzs.add(clazz);
            itemTypes.add(layoutRes);
        }
        return this;
    }

    /**
     * @param clazz     实体类型
     * @param layoutRes 对应的布局Id
     * @return
     */
    public MutiItemAdapter register(Class<T> clazz, @LayoutRes int layoutRes, @IntRange(from = 1, to = 10) int count) {
        register(clazz, layoutRes);
        if (spanSize == null) {
            spanSize = new DiffSpanSize(this);
        }
        spanSize.addItemCount(layoutRes, count);
        return this;
    }


    @Override
    public void setViewLayoutManager(@NonNull RecyclerView recyclerView) {
        setViewLayoutManager(recyclerView, true);
    }

    /**
     * 自动设置 LinearLayoutManager 或者 GridLayoutManager 以及Adapter
     *
     * @param recyclerView
     * @param isVertical
     */
    @Override
    public void setViewLayoutManager(@NonNull RecyclerView recyclerView, boolean isVertical) {
        if (spanSize == null) {
            super.setViewLayoutManager(recyclerView, isVertical);
        } else {
            spanSize.setLayoutManager(recyclerView, isVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL);
            recyclerView.setAdapter(this);
        }
    }

    public boolean isRegister(@LayoutRes int layoutRes) {
        return itemTypes.indexOf(layoutRes) != -1;
    }

    @NonNull
    private MutiItemBinder getMutiItemBinder(ViewGroup parent, @LayoutRes int layoutRes) {
        if (factory == null) {
            throw new IllegalArgumentException("必须设置MutiItemBinderFactory，或者注册MutiItemBinder");
        }
        return factory.getMutiItemBinder(layoutRes, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MutiItemBinder binder = (MutiItemBinder) holder;
        binder.onBindViewHolder(getItem(position), position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()||!(holder instanceof PayLoadBinder)) {
            onBindViewHolder(holder, position);
        } else {
            PayLoadBinder binder = (PayLoadBinder) holder;
            binder.onBindViewHolder(position, payloads.get(0));
        }
    }

    public int getItemViewType(MutiItem item) {
        int type = item.getMutiItem().layoutRes;
        if (type > 0) {
            return type;
        }
        int index = clazzs.indexOf(item.getClass());
        if (index != -1) {
            type = itemTypes.get(index);
            item.getMutiItem().layoutRes = type;
            return type;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(getItem(position));
    }
}
