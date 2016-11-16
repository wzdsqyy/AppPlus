package com.wzdsqyy.applibDemo.group;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import com.wzdsqyy.mutiitem.SessionHelper;
import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.MutiItemSuport;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GroupAdapter<M extends MutiItemSuport> extends MutiItemAdapter<M> {
    private SessionHelper helper;
    public GroupAdapter(MutiItemBinderFactory factory) {
        super(factory);
    }

    public MutiItemAdapter setSessionType(@LayoutRes int layoutRes) {
        boolean register = isRegister(layoutRes);
        if(register){
            if(helper==null){
                helper=new SessionHelper(layoutRes,this);
            }else {
                helper.setSectionType(layoutRes);
            }
        }else {
            throw new RuntimeException("must register layoutRes frist");
        }
        return this;
    }

    public SessionHelper getSessionHelper() {
        return helper;
    }
}
