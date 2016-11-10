package com.wzdsqyy.weiman.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 返回的基类
 */

public class ComicsResponse extends BaseResponse{
    @Expose
    @SerializedName("showapi_res_body")
    public Date body;
    public static class Data{
        @Expose
        @SerializedName("ret_code")
        public int code;
        @Expose
        @SerializedName("pagebean")
        public PageBean page;
    }
}
