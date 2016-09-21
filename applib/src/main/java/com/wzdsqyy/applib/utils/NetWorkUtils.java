package com.wzdsqyy.applib.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2016/1/19.
 */
public class NetWorkUtils extends BroadcastReceiver {

    public static final String EXTT_APP = "exit";
    private static NetWorkUtils netWorkStatusListener = new NetWorkUtils();
    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static boolean havaRegister = false;
    private boolean isConnect;
    private boolean isWifi;
    private HandlerThread localHandlerThread;
    private Handler localHandler;

    public boolean isConnect() {
        return isConnect;
    }

    public boolean isWifi() {
        return isConnect && isWifi;
    }

    private CopyOnWriteArrayList<NetStatusListener> listeners = new CopyOnWriteArrayList<>();

    public static NetWorkUtils instance() {
        return netWorkStatusListener;
    }

    public void register(Context context) {
        if (!havaRegister) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(CONNECTIVITY_CHANGE_ACTION);
            filter.setPriority(1000);
            refreshNetstatus(context);
            context.getApplicationContext().registerReceiver(netWorkStatusListener, filter);
            havaRegister=true;
        }
    }

    public void unregister(Context context) {
        if (havaRegister) {
            listeners.clear();
            if(localHandlerThread.isAlive()){
                localHandlerThread.quit();
            }
            context.getApplicationContext().unregisterReceiver(netWorkStatusListener);
        }
    }


    private NetWorkUtils() {
         /*初始化后台本地线程,用于本地请求*/
        localHandlerThread = new HandlerThread("EventBusHelper", android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && listeners.size() > 0) {
            refreshNetstatus(context);
            for (int i = 0; i < listeners.size(); i++) {
                listeners.get(i).onNetworkStatus(isConnect, isWifi, localHandler);
            }
        }else if(EXTT_APP.equals(intent.getAction())){
            unregister(context);
        }
    }

    private void refreshNetstatus(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = con.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected()) {
            isConnect = true;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifi = true;
            } else {
                isWifi = false;
            }
        } else {
            isConnect = false;
            isWifi = false;
        }
    }

    public NetWorkUtils addListener(NetStatusListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
        if (listeners.size() > 0 && !localHandlerThread.isAlive()) {
            localHandlerThread.start();
            localHandler = new Handler(localHandlerThread.getLooper());
        }
        return this;
    }

    public NetWorkUtils removeListener(NetStatusListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
        if (listeners.size() <= 0) {
            localHandlerThread.quit();
        }
        return this;
    }

    public interface NetStatusListener {
        /**
         * @param havanet      是否有网络
         * @param isWifi       是否是Wifi
         * @param localHandler 如果做耗时操作，用此Handler post 即可
         */
        void onNetworkStatus(boolean havanet, boolean isWifi, Handler localHandler);
    }
}
