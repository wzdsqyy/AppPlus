package com.wzdsqyy.weiman.bean;

import android.os.Parcelable;
import android.support.annotation.LayoutRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wzdsqyy.mutiitem.MutiItemSuport;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ComicsItem implements MutiItemSuport{
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

    private int layout = -1;

    @Override
    public int getMutiItemViewType() {
        return layout;
    }

    @Override
    public void setMutiItemViewType(@LayoutRes int viewType) {
        this.layout = viewType;
    }


}
