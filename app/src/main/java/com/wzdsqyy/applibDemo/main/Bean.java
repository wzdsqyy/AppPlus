package com.wzdsqyy.applibDemo.main;

import android.app.Activity;

/**
 * Created by Administrator on 2016/9/20.
 */

public class Bean {
    String name;
    Class<? extends Activity> clazz;

    public Bean(String name, Class<? extends Activity> clazz) {
        this.name = name;
        this.clazz = clazz;
    }
}
