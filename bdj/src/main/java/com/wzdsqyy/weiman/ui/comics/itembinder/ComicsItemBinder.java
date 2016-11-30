package com.wzdsqyy.weiman.ui.comics.itembinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wzdsqyy.bdj.R;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.internal.BaseRVAdapter;
import com.wzdsqyy.mutiitem.internal.MutiItemAdapter;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.ui.comics.ComicsDetailActivity;
import com.wzdsqyy.weiman.ui.comics.adapter.ImageViewAdapter;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ComicsItemBinder implements MutiItemBinder<ComicsItem>, View.OnClickListener {
    private TextView tvTitle, tvTime;
    private RecyclerView rvlist;
    private ImageViewAdapter madapter;
    private ComicsItem bean;

    @Override
    public void onClick(View v) {
        if (bean == null) {
            return;
        }
        ComicsDetailActivity.start(v.getContext(), bean.itemid, bean.title);
    }

    @Override
    public void onBindViewHolder(ComicsItem bean, int possion) {
        this.bean = bean;
        tvTime.setText(bean.time);
        tvTitle.setText(bean.title);
        madapter.setData(bean.list);
    }

    @Override
    public void init(@NonNull RecyclerView.ViewHolder holder, @NonNull MutiItemAdapter adapter) {
        tvTitle = (TextView) holder.itemView.findViewById(R.id.comics_title);
        tvTime = (TextView) holder.itemView.findViewById(R.id.comics_title);
        rvlist = (RecyclerView) holder.itemView.findViewById(R.id.thumbnailList);
        rvlist.setHasFixedSize(true);
        madapter = new ImageViewAdapter();
        madapter.setViewLayoutManager(rvlist, false);
        holder.itemView.setOnClickListener(this);
    }
}
