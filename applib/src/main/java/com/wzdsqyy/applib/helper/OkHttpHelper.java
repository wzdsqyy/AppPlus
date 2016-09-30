package com.wzdsqyy.applib.helper;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/9/30.
 */

public class OkHttpHelper {
    public static OkHttpClient.Builder getBuilder(){
       return new OkHttpClient.Builder();
    }

    public static OkHttpClient.Builder getBuilder(OkHttpClient.Builder builder){
        builder.dispatcher(new Dispatcher(AsyncTaskFixedHelper.getHelper().getExecutor()));
        return new OkHttpClient.Builder();
    }
}
