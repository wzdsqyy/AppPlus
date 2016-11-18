package com.wzdsqyy.applibDemo.group;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.internal.NodeAdapter;
import com.wzdsqyy.mutiitem.internal.SectionAdapter;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionItemBinder implements MutiItemBinder<SessionItem>, View.OnClickListener {
    private RecyclerView.Adapter adapter;
    private TextView textView;
    RecyclerView.ViewHolder holder;
    SessionItem bean;
    private Toast toast;
    private static final String TAG = "SessionItemBinder";

    public SessionItemBinder(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onBindViewHolder(SessionItem bean, int possion) {
        this.bean = bean;
        textView.setText(bean.getSession() + possion);
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
        toast=Toast.makeText(v.getContext(), "第" + adapterPosition, Toast.LENGTH_SHORT);
        toast.show();
//        int count = ((NodeAdapter) adapter).toggleExpand(bean);

        ((NodeAdapter) adapter).removeItem(adapterPosition);
//        Log.d(TAG, "onClick: "+count+"  expand"+bean.getNodeHelper().isExpand());



//        if(bean.getNodeHelper().isExpand()){
//            adapter.notifyItemRangeInserted(adapterPosition+1,count);
//        }else {
//            adapter.notifyItemRangeRemoved(adapterPosition+1,count);
//        }
    }
}
