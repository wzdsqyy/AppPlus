package com.wzdsqyy.weiman.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/9.
 */

public class Comics {
    @Expose
    @SerializedName("time")
    public String time;

    @Expose
    @SerializedName("title")
    public String title;
    @Expose
    @SerializedName("imgList")
    public ArrayList<String> imgList;
}
