package com.wzdsqyy.weiman.bean;

import com.google.android.agera.Repository;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/9.
 */

public class PageBean{
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
