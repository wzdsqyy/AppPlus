package com.wzdsqyy.applib.task;

import android.os.Handler;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Qiuyy on 2016/9/12.
 */
public class CallManager{
    private HashMap<String, GroupTaskProxy> groupList;
    private ExecutorService service;
    private static CallManager instance = null;

    private CallManager() {
    }

    /**
     * 释放资源
     */
    public static void onDestroy() {
        CallManager instance = CallManager.getInstance();
        instance.service = null;
        instance.groupList = null;
        CallManager.instance = null;
    }

    public static CallManager getInstance() {
        if (instance == null) {
            synchronized (CallManager.class) {
                if (instance == null) {
                    instance = new CallManager();
                }
            }
        }
        return instance;
    }

    public static CallManager getInstance(ExecutorService service) {
        getInstance().cheackNull(service);
        return instance;
    }

    /**
     * @param service
     * @return 为Null, 则创建一个新的。
     */
    private ExecutorService cheackNull(ExecutorService service) {
        if (service != null) {
            this.service = service;
        } else if (this.service == null) {
            this.service = Executors.newCachedThreadPool();
        }
        return this.service;
    }

    public void setService(ExecutorService service) {
        this.service = cheackNull(service);
    }

    public void excute(Runnable runnable) {
        cheackNull(null).execute(runnable);
    }

    private HashMap<String, GroupTaskProxy> getGroupList() {
        if (groupList == null) {
            groupList = new HashMap<>();
        }
        return groupList;
    }

    public <V, H> void start(Callable<V> call, CallListener<V, H> listener) {
        start(null, call, listener, null);
    }

    public <V, H> void start(Callable<V> call, CallListener<V, H> listener, H who) {
        start(null, call, listener, who, null);
    }

    public <V, H> void start(Callable<V> call, CallListener<V, H> listener, H who, Handler handler) {
        start(null, call, listener, who, null);
    }

    public <V, H> void start(ExecutorService service, Callable<V> call, CallListener<V, H> listener) {
        start(service, call, listener, null);
    }

    public <V, H> void start(ExecutorService service, Callable<V> call, CallListener<V, H> listener, H who) {
        start(service, call, listener, who, null);
    }

    public <V, H> void start(ExecutorService service, Callable<V> call, CallListener<V, H> listener, H who, Handler handler) {
        service = cheackNull(service);
        CallProxy callProxy;
        callProxy = new CallProxy(call, listener, handler, who, service);
        service.execute(callProxy);
    }

    public <V, H> void start(ExecutorService service, Future<V> future, FutureListener<V, H> listener) {
        start(service, future, listener, null);
    }

    public <V, H> void start(ExecutorService service, Future<V> future, FutureListener<V, H> listener, H who) {
        start(service, future, listener, who, null);
    }

    public <V, H> void start(ExecutorService service, Future<V> future, FutureListener<V, H> listener, H who, Handler handler) {
        start(service, future, listener, who, handler, false);
    }

    public <V, H> void start(ExecutorService service, Future<V> future, FutureListener<V, H> listener, H who, Handler handler, boolean isSticky) {
        service = cheackNull(service);
        FutureProxy futureProxy = new FutureProxy(future, listener, handler, who, isSticky);
        service.execute(futureProxy);
    }

    public <V> void addGroup(String groupName, Future<V> future) {
        addGroup(groupName, future, false);
    }

    public <V, H> void addGroup(String groupName, Future<V> future, boolean isSticky) {
        HashMap<String, GroupTaskProxy> list = getGroupList();
        GroupTaskProxy proxy = list.get(groupName);
        if (proxy == null) {
            proxy = new GroupTaskProxy(groupName, isSticky, list);
            list.put(groupName, proxy);
        }
        proxy.addTask(future);
    }

    public void checkResult(ExecutorService service,GroupListener listener, ArrayList<Future> futures,Handler handler,boolean isSticky) {
        GroupCheckerProxy proxy = new GroupCheckerProxy(handler,null);
        service.execute(proxy);
    }

    public void clearGroup(String groupName) {
        if (TextUtils.isEmpty(groupName)) {
            getGroupList().remove(groupName);
        } else {
            getGroupList().clear();
        }
    }

    public <H> void setGroupListener(String groupName, GroupListener listener) {
        setGroupListener(null, groupName, listener, null);
    }

    public <H> void setGroupListener(ExecutorService service, String groupName, GroupListener listener) {
        setGroupListener(service, groupName, listener, null);
    }

    public void setGroupListener(ExecutorService service, String groupName, GroupListener listener, Handler handler) {
        service = cheackNull(service);
        GroupTaskProxy proxy = getGroupList().get(groupName);
        if (proxy != null) {
            proxy.setGroupListener(service, listener, handler);
        }
    }
}
