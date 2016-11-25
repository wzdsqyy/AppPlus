package com.wzdsqyy.jsbridge;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求数据格式：
 * <p>
 * <pre>
 *    {
 *      "handlerName":"test",
 *      "callbackId":"c_111111",
 *      "params":{
 *          ....
 *      }
 *    }
 *
 *    hanlerName 代表java与js之间给对方暴漏的接口的名称，
 *    callbackId 代表对方在发起请求时，会为回调方法生产一个唯一的id值，它就代表这个唯一的id值
 *    params     代表传递的数据
 * </pre>
 * }
 */
class Request {
    /*request相关的属性*/
    String interfaceName;
    String callbackId;
    JSONObject requestValues;
    Object callback;

    static void init(String requestInterfaceName, String requestCallbackIdName, String requestValuesName) {
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

    void parseRequest(JSONObject json) {
        if (json != null) {
            callbackId = json.optString(Utils.sRequestCallbackIdName);
            interfaceName = json.optString(Utils.sRequestInterfaceName);
            requestValues = json.optJSONObject(Utils.sRequestValuesName);
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Utils.sRequestCallbackIdName, callbackId);
            jsonObject.put(Utils.sRequestInterfaceName, interfaceName);
            if (requestValues != null) {
                jsonObject.put(Utils.sRequestValuesName, requestValues);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "'" + jsonObject.toString() + "'";
    }
}
