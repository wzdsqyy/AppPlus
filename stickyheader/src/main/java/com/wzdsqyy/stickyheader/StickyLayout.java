package com.wzdsqyy.stickyheader;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * 局限性
 * sticky Header的高度是列表最小的若不是最小可能出现滑动闪烁的问题。
 */

public class StickyLayout extends FrameLayout implements OnScrollHandler {
    RecyclerView.ViewHolder holder;
    RecyclerView target;
    @LayoutRes int stickyItem = -1;

    public static StickyLayout newInstance(@NonNull Context context) {
        StickyLayout fragment = new StickyLayout(context);
        return fragment;
    }

    public StickyLayout(Context context) {
        this(context, -1);
    }

    public StickyLayout(Context context, @LayoutRes int stickyItem) {
        super(context);
        this.stickyItem = stickyItem;
    }

    public static StickyLayout newInstance(@NonNull Context context, @NonNull @LayoutRes int stickyItem) {
        StickyLayout fragment = new StickyLayout(context, stickyItem);
        return fragment;
    }

    public StickyLayout setTarget(RecyclerView target) {
        return setTarget(target, true);
    }

    public StickyLayout setTarget(RecyclerView target, boolean scroll) {
        return setTarget(target, null, scroll);
    }

    public StickyLayout setTarget(@NonNull RecyclerView target, ViewGroup parent, boolean scroll) {
        StickyItemListener listener = new StickyItemListener(target);
        this.target = target;
        listener.scrollListener = this;
        listener.setScroll(scroll);
        if(parent!=null){
            toViewParent(this, parent);
        }else {
            toViewParent(this, target);
        }
        return this;
    }

    @Override
    public RecyclerView.ViewHolder getStickyHolder(@LayoutRes int viewType) {
        if (holder == null) {
            holder = target.getAdapter().onCreateViewHolder(this, viewType);
            int width = target.getWidth();
            addView(this.holder.itemView,new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return holder;
    }

    public int getStickyHeight() {
        if (holder == null) {
            holder = target.getAdapter().onCreateViewHolder(this,stickyItem);
            int width = target.getWidth();
            addView(this.holder.itemView,new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        return holder.itemView.getHeight();
    }

    @Override
    public boolean isStickyItem(int type) {
        return type == stickyItem;
    }

    @Override
    public boolean hasStickyItem() {
        return stickyItem != -1;
    }

    @Override
    public void showStickyHolder(int type, boolean show) {
        if (type == -1) {
            type = stickyItem;
        }
        getStickyHolder(type).itemView.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public void setStickyTranslationY(@LayoutRes int viewType, int distance) {
        getStickyHolder(viewType).itemView.setTranslationY(distance);
    }

    private static boolean toViewParent(@Nullable ViewGroup newParent, @Nullable View target) {
        if(newParent==null||target==null||target.getParent() == null){
            return false;
        }
        ViewGroup parent = (ViewGroup) target.getParent();
        int mIndex = parent.indexOfChild(target);
        parent.removeView(target);
        if (target.getLayoutParams() != null) {
            newParent.setLayoutParams(target.getLayoutParams());
        }
        newParent.addView(target, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        parent.addView(newParent,mIndex);
        return true;
    }
}
