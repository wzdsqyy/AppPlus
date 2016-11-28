package com.wzdsqyy.jsbridgedemo;


import com.wzdsqyy.jsbridge.JavaCallback;
import com.wzdsqyy.jsbridge.annotation.InvokeJSInterface;
import com.wzdsqyy.jsbridge.annotation.Param;
import com.wzdsqyy.jsbridge.annotation.ParamCallback;

/**
 * Created by niuxiaowei on 16/8/28.
 */
public interface IInvokeJS {


    public static class City{
        @Param("cityName")
        public String cityName;

        @Param("cityProvince")
        public String cityProvince;

        public int cityId;
    }

    @InvokeJSInterface("exam")
    void exam(@Param("test") String testContent, @Param("id") int id, @ParamCallback JavaCallback iJavaCallback2JS);

    @InvokeJSInterface("exam1")
    void exam1(@Param City city, @ParamCallback JavaCallback iJavaCallback2JS);

    @InvokeJSInterface("exam2")
    void exam2(@Param City city, @Param("contry") String contry, @ParamCallback JavaCallback iJavaCallback2JS);

    @InvokeJSInterface("exam3")
    void exam3(@Param(value = "city") City city, @Param("contry") String contry, @ParamCallback JavaCallback iJavaCallback2JS);

    @InvokeJSInterface("exam4")
    void exam4(@ParamCallback JavaCallback iJavaCallback2JS);
}
