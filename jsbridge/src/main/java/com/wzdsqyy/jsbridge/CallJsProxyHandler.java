package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wzdsqyy.jsbridge.annotation.InvokeJSInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/25.
 */

class CallJsProxyHandler implements InvocationHandler {
    private SimpleJavaJsBridge bridge;
    private JsonParser parser;

    public CallJsProxyHandler(@NonNull SimpleJavaJsBridge bridge) {
        this(bridge,null);
    }

    public CallJsProxyHandler(SimpleJavaJsBridge bridge, JsonParser parser) {
        this.bridge = bridge;
        this.parser = parser;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        InvokeJSInterface annotation = method.getAnnotation(InvokeJSInterface.class);
        if (annotation == null) {
            return new JSBridgeException("没有进行 InvokeJSInterface 注解的方法，不可运行");
        }
        String jsMethodName = annotation.value();
        RequestResponseBuilder requstBuild = new RequestResponseBuilder(true);
        requstBuild.setInterfaceName(jsMethodName);
        Params params = Params.createParams(method);
        params.convertParamValues2Json(requstBuild, args);
        sendData2JS(requstBuild);
        return new Object();
    }















    /**
     * 发送数据给js
     */
    public void sendData2JS(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null) {
            return;
        }
        if (requestResponseBuilder.isBuildRequest()) {
            bridge.sendRequest2JS(requestResponseBuilder);
        } else {
            bridge.sendResponse2JS(requestResponseBuilder);
        }
    }
}
