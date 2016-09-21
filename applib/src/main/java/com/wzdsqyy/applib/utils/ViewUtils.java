package com.wzdsqyy.applib.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * Created by Qiuyy on 2016/9/13.
 */
public class ViewUtils {
    public static boolean toViewParent(@Nullable ViewGroup container, @Nullable View target) {
        if (container != null && target != null && target.getParent() != null) {
            ViewGroup parent = (ViewGroup) target.getParent();
            int groupIndex = parent.indexOfChild(target);
            parent.removeView(target);
            if (target.getLayoutParams() != null) {
                container.setLayoutParams(target.getLayoutParams());
            }
            parent.addView(container, groupIndex);
            container.addView(target, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return true;
        }
        return false;
    }

    public static void safeSetOnClickListener(@Nullable View target, @Nullable View.OnClickListener listener) {
        if (target != null) {
            target.setOnClickListener(listener);
        }
    }

    public static void safeSetOnClickListener(@IdRes int viewId, @Nullable View parent, @Nullable View.OnClickListener listener) {
        if (parent != null) {
            safeSetOnClickListener(parent.findViewById(viewId), listener);
        }
    }

    public static void safeSetOnClickListener(@IdRes int viewId, @Nullable Activity activity, @Nullable View.OnClickListener listener) {
        if (activity != null) {
            safeSetOnClickListener(activity.findViewById(viewId), listener);
        }
    }

    public static void removeParent(View v) {
        if(v.getParent()!=null){
            ((ViewGroup)v.getParent()).removeView(v);
        }
    }
}
