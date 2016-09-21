package com.wzdsqyy.applib.utils;

import android.content.Context;

/**
 * Created by Administrator on 2015/11/4.
 */
public class ContextManager {
    private static Context mAppContext;//ApplicationContext

    /**
     * @param appContext ApplicationContext 不是Activity的Context
     */
    public static void init(Context appContext) {
        if (mAppContext == null) {
            mAppContext = appContext;
        }
    }

    /**
     * @return 全局的上下文，不是Activity的上下文，生命周期是整个应用活动
     */
    public static Context getAppContext(){
        if (mAppContext == null) {
            return null;
        }else{
            return mAppContext;
        }
    }
}
