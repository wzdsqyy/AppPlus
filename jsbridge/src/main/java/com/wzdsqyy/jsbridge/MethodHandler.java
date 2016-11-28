package com.wzdsqyy.jsbridge;


import android.support.annotation.NonNull;

import java.lang.reflect.Method;

/**
 * 该类的主要作用是通过反射来调用相应的方法
 * Created by niuxiaowei on 16/7/18.
 */
class MethodHandler {

    /**
     * 方法所对应的对象实例
     */
    private Object mInstance;
    /**
     * 要调用的方法
     */
    private Method mMethod;
    /**
     * 方法所对应的参数
     */
    private Params mParams;

    public MethodHandler(Object instance, Method method, Params params) {
        mInstance = instance;
        mMethod = method;
        mParams = params;
    }


    /**
     * 构造一个{@link MethodHandler}
     *
     * @param instance
     * @param method
     * @return
     */
    public static MethodHandler createMethodHandler(@NonNull Object instance, @NonNull Method method) {
        Params params = Params.createParams(method,instance);
        return new MethodHandler(instance, method, params);
    }


    /**
     * 开始执行方法
     *
     * @param requestResponseBuilder 包含了方法的参数所对应的参数值，会把参数值依次解析出来，供方法调用
     */
    public void invoke(@NonNull RequestResponseBuilder requestResponseBuilder) throws Exception{
        Object[] values = mParams.convertJson2ParamValues(requestResponseBuilder);
        mMethod.invoke(mInstance, values);
    }
}
