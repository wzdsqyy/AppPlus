package com.wzdsqyy.mutiitem;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public abstract class BaseRVAdapter<VH extends RecyclerView.ViewHolder, M> extends RecyclerView.Adapter<VH> {
    protected List<M> mData;
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
    public List<M> getData() {
        return mData;
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(List<M> data) {
        if (data != null) {
            mData.addAll(0, data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(List<M> data) {
//        if (data != null) {
//            mData.addAll(mData.size(), data);
//            notifyItemRangeInserted(mData.size(), data.size());
//        }
        addMoreData(data,mData.size()-1);
    }

    public void addMoreData(List<M> data,int position) {
        if (data != null) {
            mData.addAll(position+1, data);
            notifyItemRangeInserted(position+1, data.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(List<M> data) {
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
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mData.add(position, model);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        addItem(mData.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        notifyItemChanged(location);
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
        notifyItemMoved(fromPosition, toPosition);
    }
}
