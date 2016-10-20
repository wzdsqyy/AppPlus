package com.wzdsqyy.applib.ui.sticky;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wzdsqyy.applib.utils.ViewUtils;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/10/18.
 */

public class StickyLayout extends FrameLayout implements OnScrollHandler{
    RecyclerView.ViewHolder holder;
    RecyclerView target;
    final int stickyItem;
    ArrayList<Integer> stickyItems;
    private boolean scroll;

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
        listener.scrollListener=this;
        listener.setScroll(true);
        if (parent == null) {
            parent = (ViewGroup) target.getParent();
        } else {
            parent = (ViewGroup) parent.getParent();
        }
        target.getAdapter().onCreateViewHolder(target, stickyItem);
        ViewUtils.toViewParent(this, parent);
        return this;
    }

    @Override
    public RecyclerView.ViewHolder getStickyHolder(int viewType) {
        if(holder==null){
            holder = target.getAdapter().onCreateViewHolder(this, stickyItem);
            addView(this.holder.itemView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.itemView.bringToFront();
        }
        return holder;
    }

    @Override
    public boolean isStickyItem(int type) {
        return type==stickyItem;
    }

    @Override
    public void setStickyTranslationY(@LayoutRes int viewType,int distance) {
        getStickyHolder(viewType).itemView.setTranslationY(distance);
//        scrollListener.getStickyHolder(type).itemView.setTranslationY(distance);
    }
}
