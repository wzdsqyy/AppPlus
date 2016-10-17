package com.wzdsqyy.applib.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/10/17.
 */

public class SimpleDialog extends Dialog {
    private final static float DEFAULT_DIM = 0.2f;
    public final static int DEFAULT_BOTTOM = 1;
    public final static int DEFAULT_CENTER = 2;
    public final static int DEFAULT_TOP = 3;
    public final static int DEFAULT_LOADING = 4;
    private float dimAmount = DEFAULT_DIM;
    private int gravity;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT, width = WindowManager.LayoutParams.MATCH_PARENT;
    private Drawable backgroundDrawable;

    public static SimpleDialog newInstance(Context context) {
        return newInstance(context, DEFAULT_CENTER);
    }

    public static SimpleDialog newInstance(Context context, int type) {
        SimpleDialog fragment = new SimpleDialog(context);
        switch (type) {
            case DEFAULT_BOTTOM:
                return fragment;
            case DEFAULT_CENTER:
                fragment.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                return fragment;
            case DEFAULT_TOP:
                return fragment;
            case DEFAULT_LOADING:
                fragment.setCanceledOnTouchOutside(false);
                fragment.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                fragment.setCancelable(false);
                return fragment;
        }
        return fragment;
    }

    public SimpleDialog(Context context) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    public SimpleDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getGravity() {
        return gravity;
    }

    public float getDimAmount() {
        return dimAmount;
    }

    public Drawable getBackgroundDrawable() {
        if (backgroundDrawable == null) {
            backgroundDrawable = new ColorDrawable(0x00000000);
        }
        return backgroundDrawable;
    }

    public SimpleDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public SimpleDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public SimpleDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public SimpleDialog setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
        return this;
    }

    @Override
    public void show() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount();
        params.width = getWidth();
        params.height = getHeight();
        params.gravity = getGravity();
        window.setAttributes(params);
        window.setBackgroundDrawable(getBackgroundDrawable());
        super.show();
    }
}
