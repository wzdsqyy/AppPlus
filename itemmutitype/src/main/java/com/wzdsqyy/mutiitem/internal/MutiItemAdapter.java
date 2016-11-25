package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
    private SparseArray<Class<? extends MutiItemBinder>> mBinders;
    private DiffSpanSize spanSize;

    public MutiItemAdapter setMutiItemBinderFactory(MutiItemBinderFactory factory) {
        this.factory = factory;
        return this;
    }

    SparseArray getBinders() {
        if(mBinders==null){
            mBinders=new SparseArray();
        }
        return mBinders;
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
     *
     * @param layoutRes
     * @param binderClass
     * @return
     */
    public MutiItemAdapter registerBinder(@LayoutRes int layoutRes,Class<MutiItemBinder<T>> binderClass) {
        getBinders().put(layoutRes,binderClass);
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

    @Override
    public RecyclerView.ViewHolder newViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        MutiItemBinder mutiItemBinder = getMutiItemBinder(parent, viewType);
        RecyclerView.ViewHolder holder;
        if (mutiItemBinder instanceof RecyclerView.ViewHolder) {
            holder = (RecyclerView.ViewHolder) mutiItemBinder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            holder = new DefaultMutiItemHolder(view).setBinder(mutiItemBinder);
        }
        mutiItemBinder.init(holder, this);
        return holder;
    }

    @NonNull
    private MutiItemBinder getMutiItemBinder(ViewGroup parent, @LayoutRes int layoutRes) {
        if(factory==null&&mBinders==null){
            throw new IllegalArgumentException("必须设置MutiItemBinderFactory，或者注册MutiItemBinder");
        }
        MutiItemBinder binder;
        if (factory != null) {
            binder=factory.getMutiItemBinder(layoutRes, parent);
            if(binder!=null){
                return binder;
            }
        }
        if(mBinders==null){
            throw new RuntimeException("该种视图没有对应的MutiItemBinder");
        }
        Class clazz = mBinders.get(layoutRes, null);
        if(clazz==null){
            throw new RuntimeException("该种视图没有注册过对应的MutiItemBinder");
        }
        try {
            return(MutiItemBinder) clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("必须含有公共的，空的构造器方法");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean isBinder = holder instanceof DefaultMutiItemHolder;
        MutiItemBinder binder = isBinder ? ((DefaultMutiItemHolder) holder).getMutiItemBinder() : (MutiItemBinder) holder;
        binder.onBindViewHolder(getItem(position), position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean isBinder = holder instanceof DefaultMutiItemHolder;
            MutiItemBinder binder;
            PayLoadBinder payLoadBinder = null;
            if (isBinder) {
                binder = ((DefaultMutiItemHolder) holder).getMutiItemBinder();
                payLoadBinder = ((DefaultMutiItemHolder) holder).getPayLoadBinder();
            } else {
                binder = (MutiItemBinder) holder;
                if (holder instanceof PayLoadBinder) {
                    payLoadBinder = (PayLoadBinder) holder;
                }
            }
            if (payLoadBinder == null) {
                payLoadBinder.onBindViewHolder(position, payloads.get(0));
            } else {
                binder.onBindViewHolder(getItem(position), position);
            }
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
