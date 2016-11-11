package com.wzdsqyy.weiman.ui.comics.itembinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemHolder;
import com.wzdsqyy.weiman.bean.Comics;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.ui.comics.ComicsDetailActivity;
import com.wzdsqyy.weiman.ui.comics.adapter.ImageViewAdapter;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ComicsItemBinder implements MutiItemBinder<ComicsItem>,View.OnClickListener {
    private TextView tvTitle,tvTime;
    private RecyclerView rvlist;
    private ImageViewAdapter adapter;
    private ComicsItem bean;
    @Override
    public void onBindViewHolder(MutiItemHolder holder, ComicsItem bean, int possion) {
        this.bean=bean;
        tvTime.setText(bean.time);
        tvTitle.setText(bean.title);
        adapter.setData(bean.list);
    }

    @Override
    public void init(MutiItemHolder holder) {
        tvTitle=holder.findViewById(R.id.comics_title);
        tvTime=holder.findViewById(R.id.comics_title);
        rvlist=holder.findViewById(R.id.thumbnailList);
        rvlist.setHasFixedSize(true);
        adapter=new ImageViewAdapter();
        adapter.setViewLayoutManager(rvlist,false);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(bean==null){
            return;
        }
        ComicsDetailActivity.start(v.getContext(),bean.itemid,bean.title);
    }
}
