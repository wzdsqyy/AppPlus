package com.wzdsqyy.jsbridge;

import com.wzdsqyy.jsbridge.annotation.ParamCallback;

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
        return Proxy.newProxyInstance(paramType.getClassLoader(), new Class<?>[]{paramType}, new CallBackHandler(resId));
    }

    @Override
    public void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) {
        if (requestResponseBuilder == null || obj == null) {
            return;
        }
        requestResponseBuilder.setRequestCallback(obj);
    }
}
