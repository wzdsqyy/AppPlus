package com.wzdsqyy.applibDemo.group;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;

import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.SectionHelper;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GroupAdapter extends MutiItemAdapter {
    private SectionHelper helper;
    public GroupAdapter(MutiItemBinderFactory factory) {
        super(factory);
    }

    public MutiItemAdapter setSessionType(@LayoutRes int layoutRes) {
        boolean register = isRegister(layoutRes);
        if(register){
            if(helper==null){
                helper=new SectionHelper(layoutRes,this);
            }else {
                helper.setSectionType(layoutRes);
            }
        }else {
            throw new RuntimeException("must register layoutRes frist");
        }
        return this;
    }

    public SectionHelper getSessionHelper() {
        return helper;
    }
}
