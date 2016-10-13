package com.wzdsqyy.applib.ui.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MutiAdapter<H> extends RecyclerView.Adapter<MutiHolder> {
    private ViewModelFactory factory;
    private ArrayList<ItemTypeSuport> datas = new ArrayList<>();
    private ArrayList<Class> clazzs = new ArrayList<>();
    private ArrayList<Integer> itemTypes = new ArrayList<>();
    private WeakReference<H> hostRef;//透传的变量

    public MutiAdapter(@NonNull ViewModelFactory factory) {
        this(null, factory);
    }

    /**
     * @param host    透传的变量，无任何意义，用户可通过 getHost() 获得引用，内部为弱引用
     * @param factory
     */
    public MutiAdapter(H host, ViewModelFactory factory) {
        this.factory = factory;
        setHost(host);
    }

    public MutiAdapter setHost(H host) {
        if (host == null) {
            return this;
        }
        this.hostRef = new WeakReference<>(host);
        return this;
    }

    public H getHost() {
        return hostRef == null ? null : hostRef.get();
    }

    /**
     *
     * @param clazz 实体类型
     * @param layoutRes 对应的布局Id
     * @return
     */
    public MutiAdapter register(Class clazz, @LayoutRes int layoutRes) {
        if(factory==null){
            throw new RuntimeException("ViewModelFactory 必须在构造函数中提供且不能为null");
        }
        int index = clazzs.indexOf(clazz);
        if (index == -1) {
            clazzs.add(clazz);
            itemTypes.add(layoutRes);
        }
        return this;
    }


    /**
     * 将会替换你设置的布局管理器
     * @param recyclerView
     * @param spanSize
     */
    public void diffSpanSizItem(@NonNull RecyclerView recyclerView,@NonNull SpanSize spanSize) {
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(), spanSize.getSpanCount());
        DiffSpanSize span = new DiffSpanSize(this, spanSize);
        manager.setSpanSizeLookup(span);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    public MutiAdapter setDatas(ArrayList<ItemTypeSuport> datas) {
        this.datas = datas;
        return this;
    }

    @Override
    public MutiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        MutiItemHolder mutiItemHolder = factory.getMutiItemHolder(viewType);
        MutiHolder<H> holder = new MutiHolder(this, view, mutiItemHolder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MutiHolder holder, int position) {
        holder.getItemHolder().onBindViewHolder(holder, datas.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        ItemTypeSuport item = datas.get(position);
        int type = item.getItemType();
        if (type > 0) {
            return type;
        }
        int index = clazzs.indexOf(item.getClass());
        if (index < 0 || index >= itemTypes.size()) {
            return super.getItemViewType(position);
        }
        type = itemTypes.get(index);
        item.setItemType(type);
        return type;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}
