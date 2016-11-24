package com.wzdsqyy.mutiitem.internal;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public abstract class BaseRVAdapter<VH extends RecyclerView.ViewHolder, M> extends RecyclerView.Adapter<VH> {
    protected List<M> mData;

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH holder = newViewHolder(parent, viewType);
        return holder;
    }

    public void setViewLayoutManager(@NonNull RecyclerView recyclerView) {
        setViewLayoutManager(recyclerView, true);
    }

    public void setViewLayoutManager(@NonNull RecyclerView recyclerView, boolean isVertical) {
        LinearLayoutManager manager=new LinearLayoutManager(recyclerView.getContext(), isVertical?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    public abstract VH newViewHolder(ViewGroup parent, @LayoutRes int viewType);

    @Override
    public int getItemCount() {
        return getData() == null ? 0 : getData().size();
    }

    public M getItem(int position) {
        return getData().get(position);
    }

    public List<M> getData() {
        return mData;
    }

    public int addNewData(List<M> data) {
        if (data != null) {
            getData().addAll(0, data);
            return data.size();
        }
        return 0;
    }

    public int addMoreData(List<M> data) {
        return addData(data, getData().size() - 1);
    }

    public int addData(List<M> data, int position) {
        if (data != null) {
            getData().addAll(position + 1, data);
            return data.size();
        }
        return 0;
    }

    public void setData(List<M> data) {
        mData=data;
    }

    public void clear() {
        getData().clear();
        notifyDataSetChanged();
    }

    public M removeItem(int position) {
        return getData().remove(position);
    }

    public boolean removeItem(M model) {
        return (getData().remove(model));
    }

    public int addItem(int position, M model) {
        getData().add(position, model);
        return 1;
    }

    public int addFirstItem(M model) {
        return addItem(0, model);
    }

    public int addLastItem(M model) {
        return addItem(getData().size(), model);
    }

    public void setItem(int location, M newModel) {
        getData().set(location, newModel);
    }

    public void setItem(M oldModel, M newModel) {
        setItem(getData().indexOf(oldModel), newModel);
    }

    public void moveItem(int fromPosition, int toPosition) {
        getData().add(toPosition, getData().remove(fromPosition));
    }
}
