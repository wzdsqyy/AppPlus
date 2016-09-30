package com.wzdsqyy.applib.helper;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Administrator on 2016/9/30.
 */

public class ExecuterGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCacheService(AsyncTaskFixedHelper.getHelper().getExecutor())
                .setResizeService(AsyncTaskFixedHelper.getHelper().getExecutor())
                .setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
