package com.wzdsqyy.applibDemo.group;

import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.ExpandItemSupport;
import com.wzdsqyy.mutiitem.MutiItemSuport;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SessionItem implements ExpandItemSupport {
    private int layoutRes = -1;
    private String session = "组";
    private List delete;

    public List getDelete() {
        return delete;
    }

    public SessionItem setDelete(List delete) {
        this.delete = delete;
        return this;
    }

    public SessionItem(int possion) {
        this.session = this.session + possion+"--";
    }

    public String getSession() {
        return session;
    }

    @Override
    public int getMutiItemViewType() {
        return layoutRes;
    }

    @Override
    public void setMutiItemViewType(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    public void setDeleteList(List<MutiItemSuport> delete) {
        this.delete=delete;
    }

    @Override
    public List<MutiItemSuport> getDeleteList() {
        return delete;
    }
}
