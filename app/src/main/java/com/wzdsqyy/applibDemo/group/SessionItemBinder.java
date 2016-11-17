package com.wzdsqyy.applibDemo.group;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.SectionAdapter;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionItemBinder implements MutiItemBinder<SessionItem>, View.OnClickListener {
    private SectionAdapter adapter;
    private TextView textView;
    RecyclerView.ViewHolder holder;
    SessionItem bean;
    private Toast toast;

    public SessionItemBinder(SectionAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onBindViewHolder(SessionItem bean, int possion) {
        this.bean = bean;
        textView.setText(bean.getSession() + adapter.getSection(possion));
    }

    @Override
    public void init(@NonNull RecyclerView.ViewHolder holder, @NonNull RecyclerView.Adapter adapter) {
        textView = (TextView) holder.itemView;
        this.holder = holder;
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = holder.getLayoutPosition();
        if(toast!=null){
            toast.cancel();
        }
        toast=Toast.makeText(v.getContext(), "第" + adapter.getSection(adapterPosition), Toast.LENGTH_SHORT);
        toast.show();
        if (adapter.isExpand(adapterPosition)) {
            adapter.setSection(adapterPosition, false);
        } else {
            adapter.setSection(adapterPosition, true);
        }
    }
}
