package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wzdsqyy.mutiitem.MutiItemBinderIntercepter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public abstract class BaseRVAdapter<VH extends RecyclerView.ViewHolder, M,L extends List<M>> extends RecyclerView.Adapter<VH> {
    protected L mData;
    private MutiItemBinderIntercepter intercepter;

    public BaseRVAdapter setViewHolderIntercepter(MutiItemBinderIntercepter intercepter) {
        this.intercepter = intercepter;
        return this;
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH holder = newViewHolder(parent, viewType);
        if(intercepter!=null){
            intercepter.afterIntercepter(holder,viewType);
        }
        return holder;
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

    public abstract VH newViewHolder(ViewGroup parent,@LayoutRes int viewType);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public M getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    L getData() {
        return mData;
    }

    /**
     * 添加到顶部
     * @param data
     * @return 数量
     */
    public int addNewData(L data) {
        if (data != null) {
            mData.addAll(0, data);
            return data.size();
        }
        return 0;
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public int addMoreData(L data) {
        return addData(data,mData.size()-1);
    }

    public int addData(L data, int position) {
        if (data != null) {
            mData.addAll(position+1, data);
            return data.size();
        }
        return 0;
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(L data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public M removeItem(int position) {
        return mData.remove(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public boolean removeItem(M model) {
        return (mData.remove(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public int addItem(int position, M model) {
        mData.add(position, model);
        return 1;
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public int addFirstItem(M model) {
        return addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public int addLastItem(M model) {
        return addItem(mData.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        mData.add(toPosition, mData.remove(fromPosition));
    }
}
