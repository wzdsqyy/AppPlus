package com.wzdsqyy.weiman.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsItem {
    @Expose
    @SerializedName("id")
    public String itemid;

    @Expose
    @SerializedName("title")
    public String title;

    @Expose
    @SerializedName("time")
    public String time;

    @Expose
    @SerializedName("link")
    public String link;//加载原网页


    @Expose
    @SerializedName("thumbnailList")
    public ArrayList<String> list;//缩略图列表
}
