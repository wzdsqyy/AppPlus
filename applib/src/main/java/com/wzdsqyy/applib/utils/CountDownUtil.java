package com.wzdsqyy.applib.utils;

import android.os.CountDownTimer;

/**
 * Created by Administrator on 2016/1/6.
 */
public class CountDownUtil extends CountDownTimer {
    private CountDownListener countDownListener;
    private boolean isFinish;

    /**
     * @param millisInFuture    总时长，单位毫秒
     * @param countDownInterval 每隔多少秒执行CountDownListener的onTick方法
     * @param countDownListener
     */
    public CountDownUtil(long millisInFuture, long countDownInterval, CountDownListener countDownListener) {
        super(millisInFuture, countDownInterval);
        this.countDownListener = countDownListener;
    }

    public CountDownUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (countDownListener != null) {
            countDownListener.onCountDownTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (countDownListener != null) {
            countDownListener.onCountDownFinish();
        }
        isFinish=true;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = null;
    }

    public interface CountDownListener {
        void onCountDownFinish();

        void onCountDownTick(long millisUntilFinished);
    }
}
