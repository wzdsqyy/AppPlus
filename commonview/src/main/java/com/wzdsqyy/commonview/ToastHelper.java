package com.wzdsqyy.commonview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

//Toast统一管理类
public class ToastHelper {
    private static ToastHelper helper;
    private Toast toast;

    private ToastHelper() {
    }

    public static ToastHelper newInstance() {
        for (; ; ) {
            if (helper == null) {
                helper = new ToastHelper();
                return helper;
            }
        }
    }

    public boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public void showShort(Context context, CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void checkToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public void showShort(Context context, @StringRes int message) {
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public void showLong(Context context, CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public void showLong(Context context, int message) {
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public void show(Context context, CharSequence message, int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public void show(Context context, int message, int duration) {
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     */
    public void showShortTOP(Context context, int message) {
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     */
    public void showShortTOP(Context context, CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast位置
     *
     * @param context
     * @param message
     */
    public void showShortGravity(Context context, int message, int gravity) {
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast位置
     *
     * @param message
     */
    public void showShortGravity(Context context, CharSequence message, int gravity) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        checkToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }
}