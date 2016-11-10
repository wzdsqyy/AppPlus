package com.wzdsqyy.weiman.data.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ModelManager {
    @Nullable
    private static <T extends Fragment> T getModel(@NonNull Class<T> model,@NonNull FragmentManager manager) {
        try {
            String name = model.getClass().getName();
            Fragment data = manager.findFragmentByTag(name);
            if (data == null) {
                data = model.newInstance();
                data.setRetainInstance(true);
                manager.beginTransaction().add(data, name).commitNowAllowingStateLoss();
            }
            return (T) data;
        } catch (Exception e) {
            throw  new RuntimeException("model 必须含有公共无参构造器");
        }
    }

    @Nullable
    public static <T extends Fragment> T getModel(@NonNull Class<T> model,@NonNull FragmentActivity activity) {
        return getModel(model,activity.getSupportFragmentManager());
    }

    @Nullable
    public static <T extends Fragment> T getModel(@NonNull Class<T> model,@NonNull Fragment fragment) {
        return getModel(model,fragment.getFragmentManager());
    }
}
