package com.wzdsqyy.applib.countdown;

/**
 * Created by Qiuyy on 2016/9/10.
 */
public interface CountDownListener {
    void onCountDownTick(int haveTime,boolean isFinish);
    boolean isCancel();
}
