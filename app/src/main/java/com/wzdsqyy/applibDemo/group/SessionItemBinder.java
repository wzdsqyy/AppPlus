package com.wzdsqyy.applibDemo.group;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.mutiitem.MutiItemBinder;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionItemBinder implements MutiItemBinder<SessionItem>, View.OnClickListener {
    private GroupAdapter adapter;
    private TextView textView;
    RecyclerView.ViewHolder holder;

    public SessionItemBinder(GroupAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onBindViewHolder(SessionItem bean, int possion) {
        textView.setText(bean.getSession());
    }

    @Override
    public void init(@NonNull RecyclerView.ViewHolder holder, @NonNull RecyclerView.Adapter adapter) {
       textView= (TextView) holder.itemView;
        this.holder=holder;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(),"第"+adapter.getSessionHelper().getSectionPossion(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
    }
}
