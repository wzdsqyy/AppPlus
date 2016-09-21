package com.wzdsqyy.applibDemo.utils;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单封装baseAdapter，减少覆盖BaseAdaper重写的方法
 */
public abstract class CommonAdapter<D> extends BaseAdapter {

    protected List<D> mBeans;

    @Override
    public int getCount() {
        return mBeans == null ? 0 : mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addBean(D bean) {
        if (mBeans != null) {
            mBeans.add(bean);
            this.notifyDataSetChanged();
        }else{
            mBeans=new ArrayList<D>();
            mBeans.add(bean);
        }
    }

    public void addBeans(List<D> beans) {
        if (mBeans != null&&beans!=null) {
            mBeans.addAll(beans);
        }
    }

    public void setBeans(List<D> beans) {
        if (beans != null) {
            mBeans = beans;
        }
        notifyDataSetChanged();
    }

    public List<D> getBeans() {
        return mBeans;
    }


    public String getTag(){
        return this.getClass().getName();
    }
}
