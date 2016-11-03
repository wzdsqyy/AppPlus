package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.applibDemo.R;

/**
 * Created by Administrator on 2016/9/29.
 */

public class SimpleAdapter extends RecyclerView.Adapter<TextViewHolder> {
    private int count=50;

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        TextViewHolder holder=new TextViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public SimpleAdapter addCount(int count) {
        this.count = this.count+count;
        return this;
    }
}
