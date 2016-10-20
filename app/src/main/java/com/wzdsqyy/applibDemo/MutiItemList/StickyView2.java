package com.wzdsqyy.applibDemo.MutiItemList;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemHolder;


/**
 * Created by Administrator on 2016/10/12.
 */

public class StickyView2 implements MutiItemBinder<StickyModel2>, View.OnClickListener {

    private StickyModel bean;
    private TextView textView;
    private MutiItemHolder holder;

    public static StickyView2 newInstance() {
        StickyView2 instance = new StickyView2();
        return instance;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), v.toString()+"position StickyModel2"+holder.getLayoutPosition(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindViewHolder(MutiItemHolder holder, StickyModel2 bean, int possion) {
        textView= (TextView) holder.itemView;
        textView.setText(bean.toString()+possion);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void init(MutiItemHolder holder) {
        this.holder=holder;
    }
}
