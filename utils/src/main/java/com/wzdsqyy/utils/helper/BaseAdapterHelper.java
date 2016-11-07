package com.wzdsqyy.utils.helper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016/11/4.
 */

public abstract class BaseAdapterHelper<M> extends BaseAdapter{
    protected List<M> mData;
    private AdapterView adapterView;

    public BaseAdapterHelper(AdapterView adapterView) {
        this.adapterView = adapterView;
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
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
            notifyDataSetChanged();
        }
    }

    public void addMoreData(List<M> data) {
        addMoreData(data,mData.size()-1);
    }

    public void addMoreData(List<M> data,int position) {
        if (data != null) {
            mData.addAll(position+1, data);
        }
    }

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
        notifyDataSetChanged();
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
        notifyDataSetChanged();
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
        notifyDataSetChanged();
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mData.set(location, newModel);
        updateItem(location,adapterView);
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
        notifyDataSetChanged();
    }

    public static void updateItem(int position, AdapterView adapterView) {
        updateItems(position, position, adapterView);
    }

    public static void updateItems(int start, AdapterView adapterView, int count) {
        updateItems(start, count + start, adapterView);
    }

    public static void updateItems(int start, int end, AdapterView adapterView) {
        int first = adapterView.getFirstVisiblePosition();
        int last = adapterView.getLastVisiblePosition();
        if (start > last || end < first) {//不在可视范围
            return;
        }
        if (end > last) {//大于可视范围的条目不更新
            end = last;
        }
        if (start < first) {//小于可视范围的条目不更新
            start = first;
        }
        while (start <= end) {
            View view = adapterView.getChildAt(start - first);
            adapterView.getAdapter().getView(start, view, adapterView);
            start++;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
