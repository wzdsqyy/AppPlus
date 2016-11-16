package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.mutiitem.MutiItemBinder;


/**
 * Created by Administrator on 2016/10/12.
 */

public class StickyView extends RecyclerView.ViewHolder implements MutiItemBinder<StickyModel>, View.OnClickListener {
    private StickyModel bean;
    private TextView textView;
    private RecyclerView.ViewHolder holder;

    public StickyView(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), v.toString()+"position"+holder.getLayoutPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindViewHolder(StickyModel bean, int possion) {
        textView= (TextView) holder.itemView;
        textView.setText(bean.toString()+possion);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void init(RecyclerView.ViewHolder holder, RecyclerView.Adapter adapter) {
        this.holder=holder;
    }
}
