package com.wzdsqyy.applibDemo.group;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.internal.BaseRVAdapter;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ItemBinder implements MutiItemBinder<Item>, View.OnClickListener {
    BaseRVAdapter adapter;
    private TextView textView;
    Toast toast;
    RecyclerView.ViewHolder holder;
    Item item;
    private boolean expand=true;
    public ItemBinder(BaseRVAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onBindViewHolder(Item bean, int possion) {
        item=bean;
        textView.setText(bean.getItem()+possion);
    }

    @Override
    public void init(@NonNull RecyclerView.ViewHolder holder, @NonNull RecyclerView.Adapter adapter) {
        textView= (TextView) holder.itemView;
        textView.setOnClickListener(this);
        this.holder=holder;
    }

    @Override
    public void onClick(View v) {
        if(toast!=null){
            toast.cancel();
        }
        int adapterPosition = holder.getLayoutPosition();
        toast = Toast.makeText(v.getContext(), "第" + adapterPosition, Toast.LENGTH_SHORT);
        toast.show();
        adapter.removeItem(adapterPosition);
    }
}
