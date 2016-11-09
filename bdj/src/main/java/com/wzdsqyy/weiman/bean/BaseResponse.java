package com.wzdsqyy.weiman.bean;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/11/9.
 */

public class BaseResponse {
    @Expose
    @SerializedName("showapi_res_code")
    public String code;
    @Expose
    @SerializedName("showapi_res_error")
    public String error;
    @Expose
    @SerializedName("showapi_res_body")
    public JsonObject body;
}
