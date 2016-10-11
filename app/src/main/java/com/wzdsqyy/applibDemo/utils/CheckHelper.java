package com.wzdsqyy.applibDemo.utils;

import android.util.SparseArray;

/**
 * Created by Qiuyy on 2016/10/10.
 */
public class CheckHelper {
    private SparseArray<Boolean> checks;
    private int possion = -1;
    private boolean single;

    public CheckHelper(boolean single) {
        if (!single) {
            checks = new SparseArray<>();
        }
        this.single = single;
    }

    public CheckHelper() {
        this(true);
    }

    public boolean isChecked(int possion) {
        if (single) {
            return possion == this.possion;
        } else {
            return checks.get(possion,false);
        }
    }
}
