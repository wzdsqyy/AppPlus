package com.wzdsqyy.utils.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.view.OrientationEventListener;

/**
 * Created by Administrator on 2016/11/22.
 */

public class OrientationEventHelper extends OrientationEventListener {
    private Activity activity;
    private boolean ignore = false;

    public OrientationEventHelper(Context context) {
        this(context, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void setEnable(boolean enable) {
        if (enable) {
            enable();
        } else {
            disable();
        }
    }

    public OrientationEventHelper(Context context, int rate) {
        super(context, rate);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
    }

    @Override
    public void onOrientationChanged(int rotation) {
        if (activity == null || ignore) {
            return;
        }
        boolean autoRotateOn = (android.provider.Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (!autoRotateOn) {
            return;
        }
        if (((rotation >= 0) && (rotation <= 30)) || (rotation >= 330)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置竖屏
            return;
        } else if (((rotation >= 230) && (rotation <= 310))) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 设置横屏
            return;
        } else if (rotation > 30 && rotation < 95) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE); // 设置反向横屏
            return;
        }
    }
}
