package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * data of bridge
 *
 * @author haoqing
 */
public class Message {
    private String responseId; //responseId
    private String responseData; //responseData
    private String data; //data of message
    private String callbackId; //callbackId
    private String handlerName; //name of handler

    private final static String CALLBACK_ID_STR = "callbackId";
    private final static String RESPONSE_ID_STR = "responseId";
    private final static String RESPONSE_DATA_STR = "responseData";
    private final static String DATA_STR = "data";
    private final static String HANDLER_NAME_STR = "handlerName";

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CALLBACK_ID_STR, getCallbackId());
        jsonObject.put(DATA_STR, getData());
        jsonObject.put(HANDLER_NAME_STR, getHandlerName());
        jsonObject.put(RESPONSE_DATA_STR, getResponseData());
        jsonObject.put(RESPONSE_ID_STR, getResponseId());
        return jsonObject.toString();
    }

    public static Message toObject(JSONObject obj) throws JSONException {
        Message m = new Message();
        m.setHandlerName(obj.has(HANDLER_NAME_STR) ? obj.getString(HANDLER_NAME_STR) : "");
        m.setCallbackId(obj.has(CALLBACK_ID_STR) ? obj.getString(CALLBACK_ID_STR) : "");
        m.setResponseData(obj.has(RESPONSE_DATA_STR) ? obj.getString(RESPONSE_DATA_STR) : "");
        m.setResponseId(obj.has(RESPONSE_ID_STR) ? obj.getString(RESPONSE_ID_STR) : "");
        m.setData(obj.has(DATA_STR) ? obj.getString(DATA_STR) : "");
        return m;
    }

    public static List<Message> toArrayList(@NonNull JSONArray array) throws JSONException {
        List<Message> list = new ArrayList<Message>();
        for (int i = 0; i < array.length(); i++) {
            Message m = new Message();
            JSONObject object = array.optJSONObject(i);
            m = m.toObject(object);
            list.add(m);
        }
        return list;
    }

    public static List<Message> toArrayList(String jsonStr) throws JSONException {
        List<Message> list = new ArrayList<Message>();
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            Message m = new Message();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            m.setHandlerName(jsonObject.has(HANDLER_NAME_STR) ? jsonObject.getString(HANDLER_NAME_STR) : null);
            m.setCallbackId(jsonObject.has(CALLBACK_ID_STR) ? jsonObject.getString(CALLBACK_ID_STR) : null);
            m.setResponseData(jsonObject.has(RESPONSE_DATA_STR) ? jsonObject.getString(RESPONSE_DATA_STR) : null);
            m.setResponseId(jsonObject.has(RESPONSE_ID_STR) ? jsonObject.getString(RESPONSE_ID_STR) : null);
            m.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR) : null);
            list.add(m);
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return handlerName != null ? handlerName.equals(message.handlerName) : message.handlerName == null;

    }

    @Override
    public int hashCode() {
        return handlerName != null ? handlerName.hashCode() : 0;
    }
}
