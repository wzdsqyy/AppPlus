package com.wzdsqyy.jsbridge;

import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;
import com.wzdsqyy.jsbridge.annotation.ParamResponseStatus;

/**
 * 基础类，定义了一些基础的数据
 */
abstract class BaseParamItem {
    /**
     * 参数所对应的类型{@link Class}
     */
    protected Class paramType;
    /**
     * 因为参数是由{@link Param},{@link ParamCallback},{@link ParamResponseStatus}其中的一个注解标注的，
     * 注解标注的参数，会以{key:value}的格式存入json中，key值就是注解的value()值，因此{@link #paramKey}来代表key值
     */
    protected String paramKey;

    public BaseParamItem(Class paramType, String paramKey) {
        this.paramType = paramType;
        this.paramKey = paramKey;
    }

    /**
     * json的格式{key:value}，该方法会从json中把value给解析出来，作为参数值
     * @param requestResponseBuilder
     * @return
     */
    public abstract Object convertJson2ParamValue(RequestResponseBuilder requestResponseBuilder) throws Exception;

    /**
     * 该方法会把参数值以{key:value}的格式存入json中
     * @param requestResponseBuilder
     * @param obj
     */
    public abstract void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) throws Exception;
}
