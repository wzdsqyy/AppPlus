package com.wzdsqyy.floatball;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/11/29.
 */

public class FloatUtils {
    public static void addFloatView(Context context, View view) {
        if (view == null) return;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = screenWidth;
        params.y = screenHeight / 2;
        params.width = 40;
        params.height = 40;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        view.setLayoutParams(params);
        windowManager.addView(view, params);
    }

    public static void removeFloatView(Context context, View view) {
        if (view == null) return;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(view);
    }
}
