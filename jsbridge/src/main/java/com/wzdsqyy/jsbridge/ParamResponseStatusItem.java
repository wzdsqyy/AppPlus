package com.wzdsqyy.jsbridge;

import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

import org.json.JSONObject;

/**
 * 对应{@link ParamResponseStatus}注解标注的参数
 */
class ParamResponseStatusItem extends ParamItem {

    public ParamResponseStatusItem(Class paramClass, String paramKey) {
        super(paramKey, paramClass);
    }

    @Override
    protected JSONObject getJson(RequestResponseBuilder requestResponseBuilder) {
        return requestResponseBuilder.getResponseStatus();
    }

    @Override
    protected void onReceiveKeyValue(RequestResponseBuilder requestResponseBuilder, String key, Object value) {
        requestResponseBuilder.putResponseStatus(key, value);
    }
}
