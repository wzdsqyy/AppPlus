package com.wzdsqyy.utils.gilde;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.wzdsqyy.utils.helper.ExecutorHelper;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/9/30.
 */

public class ExecuterGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCacheService(ExecutorHelper.getHelper().getExecutor())
                .setResizeService(ExecutorHelper.getHelper().getExecutor())
                .setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {


        try {
            GlideDrawable drawable = Glide.with(context).load("").into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
