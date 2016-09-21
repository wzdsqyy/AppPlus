package com.wzdsqyy.applib.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Qiuyy on 2016/8/5.
 */
public class AppUtils {
    private Context context;
    private AppUtils helper;
    private ReentrantLock lock = new ReentrantLock();
    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    public AppUtils attachApp(Context context) {
        if (helper == null) {
            synchronized (AppUtils.class) {
                if (helper == null) {
                    helper = new AppUtils(context);
                }
            }
        }
        AppUtils fragment = new AppUtils(context);
        return fragment;
    }

    public AppUtils getInstance() {
        if (helper == null) {
            throw new RuntimeException("请在自定义的Application中进行初始化");
        }
        return helper;
    }

    private AppUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    private ThreadPoolExecutor sBackgroundPool;

    public ThreadPoolExecutor getsBackgroundPool() {
        if (sBackgroundPool == null) {
            lock.lock();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                sBackgroundPool = (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
                if (sBackgroundPool.getRejectedExecutionHandler() == null) {
                    sBackgroundPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            Executors.newCachedThreadPool().execute(r);
                        }
                    });
                }
            } else {
                int CPU_COUNT = Runtime.getRuntime().availableProcessors();
                int CORE_POOL_SIZE = CPU_COUNT + 1;
                int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
                int KEEP_ALIVE = 1;
                sBackgroundPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
                sBackgroundPool.setCorePoolSize(CORE_POOL_SIZE);
                sBackgroundPool.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
                sBackgroundPool.setKeepAliveTime(KEEP_ALIVE, TimeUnit.SECONDS);
            }
            try {
                Field executor = AsyncTask.class.getDeclaredField("sDefaultExecutor");
                executor.setAccessible(true);
                executor.set(null, sBackgroundPool);
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
        return sBackgroundPool;
    }

    public boolean haveSource(String className) {
        ClassLoader classLoader = AppUtils.class.getClassLoader();
        try {
            Class<?> clazz = Class.forName(className, false, classLoader);
            if (clazz != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean haveEventBus3(boolean isDebug) {
        return haveSource("org.greenrobot.eventbus.EventBusBuilder");
    }

    public boolean haveGlide(Context context) {
        return haveSource("com.bumptech.glide.GlideBuilder");
    }

    public boolean haveOkhttp3() {
        return haveSource("okhttp3.OkHttpClient");
    }

    public boolean havePicasso() {
        return haveSource("com.squareup.picasso.Picasso");
    }

    /**
     * 获取应用程序名称
     */
    public String getAppName() {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取应用MetaData
     */
    public String getMetaData(Context context, String metaKey) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaKey);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 获得网络连接是否可用
     *
     * @return
     */
    public boolean hasNetwork() {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo workinfo = con.getActiveNetworkInfo();
        return workinfo != null && workinfo.isAvailable();
    }

    /**
     * 判断是否是wifi连接
     */
    public boolean checkWifiState() throws Exception {
        boolean isWifiConnect = true;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }

    /**
     * 打开网络设置界面
     */
    public void openNet(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    public boolean isAppInstall(Context context, String appPackageName) {
        if (TextUtils.isEmpty(appPackageName) || context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(appPackageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void runOnBackground(Runnable runnable) {
        getsBackgroundPool().execute(runnable);
    }

    public void runOnUi(Runnable runnable) {
        mUiHandler.post(runnable);
    }

    //隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    //显示虚拟键盘
    public static void ShowKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }
}
