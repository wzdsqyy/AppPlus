package com.wzdsqyy.jsbridge;

import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;
import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

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

    static Params throwAnnotationError() {
        throw new IllegalArgumentException("方法的所有参数必须都得用" + Param.class.getSimpleName() + "," + ParamCallback.class.getSimpleName() + "," + ParamResponseStatus.class.getSimpleName() + " 中的任意一个注解进行标注");
    }
}
