package com.wzdsqyy.jsbridge;

import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;
import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/25.
 */

class Utils {
    /********************Request****************/
    static String sRequestInterfaceName = "handlerName";
    static String sRequestCallbackIdName = "callbackId";
    static String sRequestValuesName = "params";
    /********************Response****************/
    static String sResponseIdName = "responseId";
    static String sResponseValuesName = "values";
    static String sResponseName = "data";
    /**
     * java调用js的功能时，java会为js提供回调函数，但是不可能把回调函数传递给js，
     * 所以为回调函数提供一个唯一的id，
     */
    static int sUniqueCallbackId = 1;
    static Params throwAnnotationError() {
        throw new IllegalArgumentException("方法的所有参数必须都得用" + Param.class.getSimpleName() + "," + ParamCallback.class.getSimpleName() + "," + ParamResponseStatus.class.getSimpleName() + " 中的任意一个注解进行标注");
    }

    /**
     * 该对象是否可以直接往json中放
     *
     * @param type
     * @return
     */
    static boolean isObjectDirectPut2Json(Class type) {
        return (type == String.class || type.isPrimitive() || type == JSONArray.class || type == JSONObject.class);
    }

    /**
     * 该对象是否可以直接往json中放
     *
     * @param object
     * @return
     */
    static boolean isObjectDirectPut2Json(Object object) {
        if (object instanceof String || object instanceof Integer || object instanceof Double || object instanceof Long ||
                object instanceof Boolean || object instanceof JSONArray || object instanceof JSONObject) {
            return true;
        }
        return false;
    }

    /**
     * 生成唯一的回调id
     *
     * @return
     */
    static String generaUniqueCallbackId() {
        return ++sUniqueCallbackId + "_" + System.currentTimeMillis();
    }
}
