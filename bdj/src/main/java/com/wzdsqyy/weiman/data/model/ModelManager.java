package com.wzdsqyy.weiman.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ModelManager{
    @Nullable
    private static <T extends Fragment> T getModel(@NonNull Class<T> model, @NonNull FragmentManager manager) throws Exception {
        String name = model.getClass().getName();
        Fragment data = manager.findFragmentByTag(name);
        if (data == null) {
            data = model.newInstance();
            data.setRetainInstance(true);
            manager.beginTransaction().add(data, name).commitNowAllowingStateLoss();
        }
        return (T) data;
    }
}
