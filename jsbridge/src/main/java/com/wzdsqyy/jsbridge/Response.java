package com.wzdsqyy.jsbridge;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * response数据格式：
 * <pre>
 *  {
 *      "responseId":"iii",
 *      "data":{
 *          "status":"1",
 *          "msg":"ok",
 *          "values":{
 *              ......
 *          }
 *      }
 *  }
 *
 *  responseId 代表request中的callbackId
 *  data       代表响应的数据
 *  status     代表响应状态
 *  msg        代表响应状态对应的消息
 *  values     代表响应数据包含的值
 * </pre>
 */
class Response {


    String responseId;
    JSONObject response = new JSONObject();
    JSONObject responseValues;

    static void init(String responseIdName, String responseName, String responseValuesName) {
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

    void parseResponse(JSONObject json) {
        if (json != null) {
            responseId = json.optString(Utils.sResponseIdName);
            response = json.optJSONObject(Utils.sResponseName);
            if (response != null) {
                responseValues = response.optJSONObject(Utils.sResponseValuesName);
            }
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Utils.sResponseIdName, responseId);
            if (responseValues != null) {
                response.put(Utils.sResponseValuesName, responseValues);
            }
            jsonObject.put(Utils.sResponseName, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "'" + jsonObject.toString() + "'";
    }
}
