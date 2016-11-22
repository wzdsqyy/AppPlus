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
        if (intercepter != null) {
            intercepter.afterIntercepter(holder, viewType);
        }
        return holder;
    }

    public void setViewLayoutManager(@NonNull RecyclerView recyclerView) {
        setViewLayoutManager(recyclerView, true);
    }

    public void setViewLayoutManager(@NonNull RecyclerView recyclerView, boolean isVertical) {
        LinearLayoutManager manager;
        if (isVertical) {
            manager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            manager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        }
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    public abstract VH newViewHolder(ViewGroup parent, @LayoutRes int viewType);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public M getItem(int position) {
        return mData.get(position);
    }

    public List<M> getData() {
        return mData;
    }

    public int addNewData(List<M> data) {
        if (data != null) {
            mData.addAll(0, data);
            return data.size();
        }
        return 0;
    }

    public int addMoreData(List<M> data) {
        return addData(data, mData.size() - 1);
    }

    public int addData(List<M> data, int position) {
        if (data != null) {
            mData.addAll(position + 1, data);
            return data.size();
        }
        return 0;
    }

    public void setData(List<M> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public M removeItem(int position) {
        return mData.remove(position);
    }

    public boolean removeItem(M model) {
        return (mData.remove(model));
    }

    public int addItem(int position, M model) {
        mData.add(position, model);
        return 1;
    }

    public int addFirstItem(M model) {
        return addItem(0, model);
    }

    public int addLastItem(M model) {
        return addItem(mData.size(), model);
    }

    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
    }

    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    public void moveItem(int fromPosition, int toPosition) {
        mData.add(toPosition, mData.remove(fromPosition));
    }
}
