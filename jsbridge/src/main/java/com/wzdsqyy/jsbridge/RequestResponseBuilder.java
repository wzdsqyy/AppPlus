package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * java与js之间可以进行互相通信，主动发起通信时，传输的数据我们称作request(请求数据)对应{@link Request},当把处理结果进行返回时的数据我们称作response(响应数据)
 * 对应{@link Response},因此该类的主要作用就是用来构建request或者response数据的。每次只能构建request或者response其中一种数据，
 * {@link RequestResponseBuilder}可以从json中解析出相应的数据，也可以转化为json
 *
 * Created by niuxiaowei on 16/9/14.
 */
class RequestResponseBuilder {


    /**
     * 是否是构建request请求
     */
     boolean mIsBuildRequest;

    /**
     * 请求数据
     */
    Request mRequest;
    /**
     * 响应数据
     */
     Response mResponse;

    public RequestResponseBuilder(boolean isBuildRequest){
        this(isBuildRequest,null);
    }

    /**
     * @param isBuildRequest 是否是构造请求数据
     * @param data json数据
     */
    public RequestResponseBuilder(boolean isBuildRequest,@NonNull JSONObject data) {
        mIsBuildRequest = isBuildRequest;
        if (mIsBuildRequest) {
            mRequest = new Request();
            mRequest.parseRequest(data);
        } else {
            mResponse = new Response();
            mResponse.parseResponse(data);
        }
    }


    /**
     * 获取请求时为回调函数生成的 callbackId
     * @return
     */
    public String getCallbackId(){
        return mRequest == null?null: mRequest.callbackId;
    }

    /**
     * 获取请求的接口的名字
     * @return
     */
    public String getInterfaceName() {
        return mRequest == null ? null : mRequest.interfaceName;
    }

    public void setRequestCallback(Object callback) {
        initRequest();
        this.mRequest.callback = callback;
    }

     void initRequest() {
        if (mRequest == null) {
            mRequest = new Request();
        }
    }

    /**
     * 设置请求的接口的名字
     * @param interfaceName
     */
    public void setInterfaceName(String interfaceName) {
        initRequest();
        this.mRequest.interfaceName = interfaceName;
    }

    /**
     * 为回调方法设置回调id
     * @param callbackId
     */
    public void setCallbackId(String callbackId) {
        initRequest();
        this.mRequest.callbackId = callbackId;
    }


    /**
     * 获取request或者response的 values值
     * @return
     */
    public JSONObject getValues() {
        if (mIsBuildRequest) {
            return mRequest == null ? null : mRequest.requestValues;
        } else {
            return mResponse == null ? null : mResponse.responseValues;
        }
    }

    /**
     * 往request或者response中存放 数据
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        if(TextUtils.isEmpty(key) || value == null){
            return;
        }
        JSONObject values;
        if (mIsBuildRequest) {
            initRequest();
            if (mRequest.requestValues == null) {
                mRequest.requestValues = new JSONObject();
            }
            values = mRequest.requestValues;
        } else {
            initResponse();
            if (mResponse.responseValues == null) {
                mResponse.responseValues = new JSONObject();
            }
            values = mResponse.responseValues;
        }

        try {
            values.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Object getCallback() {
        return mRequest == null ? null : mRequest.callback;
    }

    /**
     * @param responseIdName
     * @param responseName
     * @param responseValuesName
     */
    public static void init(String responseIdName, String responseName, String responseValuesName, String requestInterfaceName, String requestCallbackIdName, String requestValuesName) {
        Response.init(responseIdName, responseName, responseValuesName);
        Request.init(requestInterfaceName, requestCallbackIdName, requestValuesName);
    }


     void initResponse() {
        if (mResponse == null) {
            mResponse = new Response();
        }
    }

    public String getResponseId() {
        return mResponse == null ? null : mResponse.responseId;
    }

    public void setResponseId(String responseId) {
        initResponse();
        this.mResponse.responseId = responseId;
    }

    /**
     * 获取response的 状态数据
     * @return
     */
    public JSONObject getResponseStatus() {
        return mResponse == null ? null : mResponse.response;
    }

    /**
     * 往response中存放 数据
     * @param key
     * @param value
     */
    public void putResponseStatus(String key, Object value) {
        if(TextUtils.isEmpty(key) || value == null){
            return;
        }
        initResponse();
        try {
            mResponse.response.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从json中创建一个{@link RequestResponseBuilder}对象，其实最终创建的是一个 request或者response
     * @param json
     * @return
     */
     static RequestResponseBuilder create(@NonNull JSONObject json) {
        if (json.has(Response.sResponseIdName)) {///响应数据
            return new RequestResponseBuilder(false, json);
        } else {
            return new RequestResponseBuilder(true, json);
        }
    }

    /**
     * 是否构建的时request数据
     * @return
     */
    public boolean isBuildRequest() {
        return mIsBuildRequest;
    }

    @Override
    public String toString() {
        if (mIsBuildRequest) {
            return mRequest == null ? super.toString() : mRequest.toString();
        } else {
            return mResponse == null ? super.toString() : mResponse.toString();
        }
    }
}
