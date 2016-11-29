package com.wzdsqyy.floatball;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatBallService extends AccessibilityService implements View.OnLongClickListener,View.OnClickListener{
    private static final String ACTION="FloatBallService_action";
    private FloatBallView floatBallView;
    public static final int ACTION_BACK = 1;
    public static final int ACTION_HOME = 2;
    public static final int ACTION_RECENTS = 3;
    public static final int ACTION_NOTIFICATIONS = 4;

    @Override
    public boolean onLongClick(View view) {
        if(view==floatBallView){
            floatBallView.setMoveing(true);
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @IntDef({ACTION_BACK,ACTION_HOME,ACTION_RECENTS,ACTION_NOTIFICATIONS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ACTION{};
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        if(floatBallView!=null){
            FloatUtils.removeFloatView(this,floatBallView);
        }
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        if(floatBallView==null){
            floatBallView=new FloatBallView(this);
            FloatUtils.addFloatView(this,floatBallView);
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int action = intent.getIntExtra(ACTION, 0);
        switch (action){
            case ACTION_BACK:
                doBack();
                break;
            case ACTION_NOTIFICATIONS:
                doPullDown();
                break;
            case ACTION_HOME:
                doPullUp();
                break;
            case ACTION_RECENTS:
                doLeftOrRight();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static void action(Context context,@ACTION int action) {
        Intent starter = new Intent(context, FloatBallService.class);
        starter.putExtra(ACTION,action);
        context.startService(starter);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FloatBallService.class);
        context.startService(starter);
    }

    public static void stop(Context context) {
        Intent starter = new Intent(context, FloatBallService.class);
        context.stopService(starter);
    }

    private void doBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
    }

    private void doPullDown() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
        }
    }

    private void doPullUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        }
    }

    private void doLeftOrRight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }
}
