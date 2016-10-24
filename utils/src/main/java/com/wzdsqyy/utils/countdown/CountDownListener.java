package com.wzdsqyy.utils.countdown;

/**
 * Created by Qiuyy on 2016/9/10.
 */
public interface CountDownListener {

    /**
     * @param time
     * @param isFinish
     */
    void onCountDownTick(long time, boolean isFinish);

    boolean isCancel();

    TimerSupport getTimerSupport();
}
