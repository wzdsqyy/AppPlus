package com.wzdsqyy.jsbridge;

import android.net.Uri;
import android.text.TextUtils;

import com.wzdsqyy.jsbridge.annotation.InvokeJSInterface;
import com.wzdsqyy.jsbridge.annotation.JavaInterface4JS;
import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;
import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/25.
 */

class Utils {
    /********************
     * Request
     ****************/
    static String sRequestInterfaceName = "handlerName";
    static String sRequestCallbackIdName = "callbackId";
    static String sRequestValuesName = "params";
    /********************
     * Response
     ****************/
    static String sResponseIdName = "responseId";
    static String sResponseValuesName = "values";
    static String sResponseName = "data";
    /**
     * java调用js的功能时，java会为js提供回调函数，但是不可能把回调函数传递给js，
     * 所以为回调函数提供一个唯一的id，
     */
    static int sUniqueCallbackId = 1;

    static void init(String responseIdName, String responseName, String responseValuesName, String requestInterfaceName, String requestCallbackIdName, String requestValuesName) {
        Utils.initResponse(responseIdName, responseName, responseValuesName);
        Utils.initRequest(requestInterfaceName, requestCallbackIdName, requestValuesName);
    }

    static void checkProtocol(String protocol ) {
        if (TextUtils.isEmpty(protocol)) {
            throw new JSBridgeException("必须调用setProtocol(String)设置协议");
        }
        Uri uri = Uri.parse(protocol);
        if (TextUtils.isEmpty(uri.getScheme()) || TextUtils.isEmpty(uri.getHost()) || !protocol.endsWith("?")) {
            throw new IllegalArgumentException("协议的格式必须是 scheme://host? 这种格式");
        }
    }

    static void checkJSMethod(String mJSMethodName4Java) {
        if (TextUtils.isEmpty(mJSMethodName4Java)) {
            throw new IllegalArgumentException("必须调用 setJSMethodName4Java(String) 方法对给js发送消息的方法进行设置");
        }

    }

    static void initResponse(String responseIdName, String responseName, String responseValuesName) {
        if (!TextUtils.isEmpty(responseValuesName)) {
            Utils.sResponseValuesName = responseValuesName;
        }
        if (!TextUtils.isEmpty(responseIdName)) {
            Utils.sResponseIdName = responseIdName;
        }
        if (!TextUtils.isEmpty(responseName)) {
            Utils.sResponseName = responseName;
        }
    }

    static void initRequest(String requestInterfaceName, String requestCallbackIdName, String requestValuesName) {
        if (!TextUtils.isEmpty(requestCallbackIdName)) {
            Utils.sRequestCallbackIdName = requestCallbackIdName;
        }

        if (!TextUtils.isEmpty(requestValuesName)) {
            Utils.sRequestValuesName = requestValuesName;
        }
        if (!TextUtils.isEmpty(requestInterfaceName)) {
            Utils.sRequestInterfaceName = requestInterfaceName;
        }
    }

    static Params throwAnnotationError() {
        throw new IllegalArgumentException("方法的所有参数必须都得用" + Param.class.getSimpleName() + "," + ParamCallback.class.getSimpleName() + "," + ParamResponseStatus.class.getSimpleName() + " 中的任意一个注解进行标注");
    }

    public static void throwJavaInterface4JS() {
        throw new IllegalArgumentException("所有方法必须都得用" + JavaInterface4JS.class.getSimpleName() + "注解进行标注");
    }
    public static void throwInvokeJSInterface() {
        throw new IllegalArgumentException("方法必须用" + InvokeJSInterface.class.getSimpleName() + "注解进行标注");
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
