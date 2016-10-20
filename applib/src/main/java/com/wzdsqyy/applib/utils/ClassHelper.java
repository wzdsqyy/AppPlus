package com.wzdsqyy.applib.utils;

/**
 * Created by Administrator on 2016/10/20.
 */

public class ClassHelper {
    public boolean haveSource(String clazz) {
        ClassLoader classLoader = ClassHelper.class.getClassLoader();
        try {
            Class<?> cla = Class.forName(clazz, false, classLoader);
            if (cla != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean haveEventBus3() {
        return haveSource("org.greenrobot.eventbus.EventBusBuilder");
    }

    public boolean haveGlide() {
        return haveSource("com.bumptech.glide.GlideBuilder");
    }

    public boolean haveOkhttp3() {
        return haveSource("okhttp3.OkHttpClient");
    }

    public boolean havePicasso() {
        return haveSource("com.squareup.picasso.Picasso");
    }

}
