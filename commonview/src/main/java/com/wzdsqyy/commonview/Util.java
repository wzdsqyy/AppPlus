package com.wzdsqyy.commonview;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Administrator on 2016/11/30.
 */

public class Util {
    public static int getSize(int Spec, int desiredWidth) {
        int mode = View.MeasureSpec.getMode(Spec);
        int size = View.MeasureSpec.getSize(Spec);
        if (mode == View.MeasureSpec.AT_MOST) {
            size = Math.min(desiredWidth, size);
        } else if (mode == View.MeasureSpec.UNSPECIFIED) {
            size = desiredWidth;
        }
        return size;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 用来判断是否可以下拉
     */
    public static boolean canScrollUp(View target) {
        if (target == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (target instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) target;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(target, -1) || target.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(target, -1);
        }
    }

    /**
     * Whether it is possible for the child view of this layout to scroll down. Override this if the child view is a custom view.
     * 判断是否可以上拉
     */
    public static boolean canScrollDown(View target) {
        if (Build.VERSION.SDK_INT < 14) {
            if (target instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) target;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(target, 1) || target.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(target, 1);
        }
    }
}
