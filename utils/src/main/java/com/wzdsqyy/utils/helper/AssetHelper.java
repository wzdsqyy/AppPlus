package com.wzdsqyy.utils.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/28.
 */

public class AssetHelper {
    static Class<?> clazz;
    static Method addAssetPath;
    static Method ensureStringBlocks;

    static {
        try {
            clazz = Class.forName("android.content.res.AssetManager");
            addAssetPath = clazz.getDeclaredMethod("addAssetPath", String.class);
            ensureStringBlocks=clazz.getDeclaredMethod("ensureStringBlocks");
        } catch (Exception e) {
        }
    }

    public static Resources addAssetPath(String path, Context context) {
        AssetManager manager;
        try {
            manager = (AssetManager) clazz.newInstance();
            addAssetPath.invoke(manager, path);
            Resources res = context.getResources();
            Constructor<?> constructor_Resources = Resources.class
                    .getConstructor(clazz, res.getDisplayMetrics()
                            .getClass(), res.getConfiguration().getClass());
            res = (Resources) constructor_Resources.newInstance(manager,
                    res.getDisplayMetrics(), res.getConfiguration());
            return res;
        } catch (Exception e) {

        }
        return context.getResources();
    }
}
