package com.wzdsqyy.jsbridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/25.
 */

class CallBackHandler implements InvocationHandler {

    private String resId;

    public CallBackHandler(String resId) {
        this.resId = resId;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestResponseBuilder response = new RequestResponseBuilder(false);
        response.setResponseId(resId);
        Params params = Params.createParams(method,proxy);
        params.convertParamValues2Json(response, args);
        if (Params.sSimpleJavaJsBridge != null) {
            Params.sSimpleJavaJsBridge.sendData2JS(response);
        } else {
            throw new JSBridgeException(SimpleJavaJsBridge.class.getName() + "必须得进行初始化");
        }
        return new Object();
    }
}
