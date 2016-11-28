package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;


import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;
import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 该类会把{@link Method}的用{@link Param},{@link ParamCallback},{@link ParamResponseStatus}这几个注解标注的param解析出来，
 * {@link Param}解析为{@link ParamItem},{@link ParamCallback}解析为{@link ParamCallbackItem},{@link ParamResponseStatus}
 * 解析为{@link ParamResponseStatusItem}。
 * <p>同时该类还有把一个json转化为参数值的功能，和把参数值转化为json的功能</p>
 * <p>
 * Created by niuxiaowei on 16/7/14.
 */
class Params {

    /**
     * 解析出来的所有注解item
     */
    BaseParamItem[] mParamItems;
    static SimpleJavaJsBridge sSimpleJavaJsBridge;
    private JsonParser jsonParser;

    Params(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    Params() {
    }

    /**
     * 初始化方法
     *
     * @param simpleJavaJsBridge
     */
    public static void init(SimpleJavaJsBridge simpleJavaJsBridge) {
        sSimpleJavaJsBridge = simpleJavaJsBridge;
    }

    /**
     * 把json转化为参数值
     *
     * @param requestResponseBuilder 包含了一系列的json数据，json数据是request或者response
     * @return
     */
    public Object[] convertJson2ParamValues(RequestResponseBuilder requestResponseBuilder) throws Exception{
        if (requestResponseBuilder == null || mParamItems == null) {
            return null;
        }
        Object[] result = new Object[mParamItems.length];
        BaseParamItem paramItem;
        for (int i = 0; i < mParamItems.length; i++) {
            paramItem = mParamItems[i];
            if (paramItem == null) {
                continue;
            }
            result[i] = paramItem.convertJson2ParamValue(requestResponseBuilder);
        }
        return result;

    }

    /**
     * 把参数值转化为json
     *
     * @param requestResponseBuilder
     * @param paramValues            参数值
     */
    public void convertParamValues2Json(RequestResponseBuilder requestResponseBuilder, Object[] paramValues) throws Exception{
        if (requestResponseBuilder == null || paramValues == null) {
            return;
        }
        BaseParamItem paramItem;
        for (int i = 0; i < mParamItems.length; i++) {
            paramItem = mParamItems[i];
            if (paramItem != null) {
                paramItem.convertParamValue2Json(requestResponseBuilder, paramValues[i]);
            }
        }
    }


    /**
     * 从{@link Method}中解析它所包含的参数
     *
     * @param method
     * @return
     */
    static Params createParams(@NonNull Method method, @NonNull Object proxy) {
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
                return Utils.throwAnnotationError();
            }
            for (int j = 0; j < annotations[i].length; j++) {
                annotation = annotations[i][j];
                if(annotation==null){
                    continue;
                }
                if (annotation instanceof Param) {
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
}
