package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wzdsqyy.jsbridge.annotation.InvokeJSInterface;
import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;
import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

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
        if (annotation == null) {
            return new JSBridgeException("没有进行 InvokeJSInterface 注解的方法，不可运行");
        }
        String jsMethodName = annotation.value();
        RequestResponseBuilder requstBuild = new RequestResponseBuilder(true);
        requstBuild.setInterfaceName(jsMethodName);
        Params params = createParams(method);
        params.convertParamValues2Json(requstBuild, args);
        sendData2JS(requstBuild);
        return new Object();
    }

    private Params createParams(Method method) throws Throwable {
        Annotation[][] annotations = method.getParameterAnnotations();
        Class[] parameters = method.getParameterTypes();
        Params params = new Params();
        if (parameters == null) {
            return params;
        }
        if (annotations == null || annotations.length != parameters.length) {
            Utils.throwAnnotationError();
        }
        params.mParamItems = new BaseParamItem[annotations.length];
        Annotation annotation;
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].length == 0) {
                Utils.throwAnnotationError();
            }
            for (int j = 0; j < annotations[i].length; j++) {
                annotation = annotations[i][j];
                if (annotation != null && annotation instanceof Param) {
                    params.mParamItems[i] = new ParamItem(((Param) annotation).value(), parameters[i]);
                } else if (annotation instanceof ParamCallback) {
                    params.mParamItems[i] = new ParamCallbackItem(parameters[i], null);
                } else if (annotation instanceof ParamResponseStatus) {
                    params.mParamItems[i] = new ParamResponseStatusItem(parameters[i], ((ParamResponseStatus) annotation).value());
                }
            }
        }
        return params;
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
