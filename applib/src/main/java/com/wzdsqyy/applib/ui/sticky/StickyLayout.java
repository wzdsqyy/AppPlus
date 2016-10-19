package com.wzdsqyy.applib.ui.sticky;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wzdsqyy.applib.utils.ViewUtils;


/**
 * Created by Administrator on 2016/10/18.
 */

public class StickyLayout extends FrameLayout implements OnScrollHandler{
    RecyclerView.ViewHolder holder;
    RecyclerView target;
    final int stickyItem;

    public StickyLayout(Context context, @LayoutRes int stickyItem) {
        super(context);
        this.stickyItem = stickyItem;
    }

    public static StickyLayout newInstance(@NonNull Context context, @NonNull @LayoutRes int stickyItem) {
        StickyLayout fragment = new StickyLayout(context, stickyItem);
        return fragment;
    }

    public StickyLayout setTarget(RecyclerView target) {
        return setTarget(target, null);
    }

    public StickyLayout setTarget(@NonNull RecyclerView target, ViewGroup parent) {
        StickyItemListener listener = new StickyItemListener(target);
        this.target = target;
        listener.stickyItemViewType=stickyItem;
        listener.previousPosition=0;
        listener.scrollListener=this;
        if (parent == null) {
            parent = (ViewGroup) target.getParent();
        } else {
            parent = (ViewGroup) parent.getParent();
        }
        ViewUtils.toViewParent(this, parent);
        return this;
    }

    @Override
    public RecyclerView.ViewHolder getStickyHolder() {
        return holder;
    }

    @Override
    public void setStickyHolder(RecyclerView.ViewHolder holder) {
        if(holder==null){
            return;
        }
        addView(holder.itemView);
        holder.itemView.bringToFront();
//        holder.itemView.setVisibility(GONE);
        this.holder=holder;
    }

    @Override
    public boolean isStickyItem(int type) {
        return type==stickyItem;
    }
}
