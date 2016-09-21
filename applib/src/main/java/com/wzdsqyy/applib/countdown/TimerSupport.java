package com.wzdsqyy.applib.countdown;

/**
 * Created by Administrator on 2016/9/21.
 */

public interface TimerSupport{
    /**
     *
     * @return 秒
     */
    int countDownInterval();

    /**
     *
     * @return 倒计时的总共时间
     */
    int getTotalTime();
}
