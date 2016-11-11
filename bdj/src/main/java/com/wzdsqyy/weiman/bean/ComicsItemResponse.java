package com.wzdsqyy.weiman.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 返回的基类
 */

public class ComicsItemResponse extends BaseResponse{
    @Expose
    @SerializedName("showapi_res_body")
    public Data body;
    public static class Data{
        @Expose
        @SerializedName("ret_code")
        public int code;
        @Expose
        @SerializedName("pagebean")
        public PageBean page;
    }

    /**
     * Created by Administrator on 2016/11/9.
     */

    public static class PageBean{
        @Expose
        @SerializedName("currentPage")
        public int page;
        @Expose
        @SerializedName("hasMorePage")
        public boolean more;
        @Expose
        @SerializedName("maxResult")
        public long count;
        @Expose
        @SerializedName("contentlist")
        public ArrayList<ComicsItem> items;
    }
}
