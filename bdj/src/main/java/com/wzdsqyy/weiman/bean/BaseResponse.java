package com.wzdsqyy.weiman.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/11/10.
 */

public abstract class BaseResponse {
    @Expose
    @SerializedName("showapi_res_code")
    public String code;
    @Expose
    @SerializedName("showapi_res_error")
    public String error;
}
