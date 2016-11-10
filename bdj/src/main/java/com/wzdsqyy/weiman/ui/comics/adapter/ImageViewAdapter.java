package com.wzdsqyy.weiman.ui.comics.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzdsqyy.mutiitem.BaseRVAdapter;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ImageViewAdapter extends BaseRVAdapter<ImageViewAdapter.ImageHolder,String>{
    @Override
    public ImageHolder newViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        ImageView imageView=new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(getItem(position)).into((ImageView) holder.itemView);
    }

    public static class ImageHolder extends RecyclerView.ViewHolder{

        public ImageHolder(View itemView) {
            super(itemView);
        }
    }
}
