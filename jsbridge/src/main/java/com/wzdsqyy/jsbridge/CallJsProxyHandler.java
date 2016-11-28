package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;

import com.wzdsqyy.jsbridge.annotation.InvokeJSInterface;
import com.wzdsqyy.jsbridge.annotation.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/25.
 */

class CallJsProxyHandler implements InvocationHandler {
    private SimpleJavaJsBridge bridge;

    public CallJsProxyHandler(@NonNull SimpleJavaJsBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        InvokeJSInterface annotation = method.getAnnotation(InvokeJSInterface.class);
        String jsMethodName = annotation.value();//Js提供给本地调用的方法名
        RequestResponseBuilder requstBuild = new RequestResponseBuilder(true);
        requstBuild.setInterfaceName(jsMethodName);
        Params params = Params.createParams(method,o);
        params.convertParamValues2Json(requstBuild, args);
        sendData2JS(requstBuild);
        return new Object();
    }
    /**
     * 发送数据给js
     */
    public void sendData2JS(@NonNull RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder.isBuildRequest()) {
            bridge.sendRequest2JS(requestResponseBuilder);
        } else {
            bridge.sendResponse2JS(requestResponseBuilder);
        }
    }
}
