package com.wzdsqyy.jsbridge;

import com.wzdsqyy.jsbridge.annotation.ParamCallback;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 对应{@link ParamCallback}注解标注的参数
 */
class ParamCallbackItem extends BaseParamItem {


    public ParamCallbackItem(Class callbackClass, String paramKey) {
        super(callbackClass, paramKey);
    }

    @Override
    public Object convertJson2ParamValue(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null || requestResponseBuilder.getCallbackId() == null) {
            return null;
        }
        final String resId = requestResponseBuilder.getCallbackId();
        return Proxy.newProxyInstance(paramType.getClassLoader(), new Class<?>[]{paramType},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        RequestResponseBuilder response = new RequestResponseBuilder(false);
                        response.setResponseId(resId);
                        Params params = Params.createParams(method);
                        params.convertParamValues2Json(response, args);
                        if (Params.sSimpleJavaJsBridge != null) {

                            Params.sSimpleJavaJsBridge.sendData2JS(response);
                        } else {
                            throw new JSBridgeException(SimpleJavaJsBridge.class.getName() + "必须得进行初始化");
                        }
                        return new Object();
                    }
                }

        );
    }

    @Override
    public void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) {
        if (requestResponseBuilder == null || obj == null) {
            return;
        }
        requestResponseBuilder.setRequestCallback(obj);
    }
}
