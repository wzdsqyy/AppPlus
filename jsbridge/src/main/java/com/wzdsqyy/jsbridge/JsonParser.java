package com.wzdsqyy.jsbridge;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface JsonParser {
    String toJsonString(Object obj) throws Exception;
    Object toObject(String json,Class clazz) throws Exception;
}
