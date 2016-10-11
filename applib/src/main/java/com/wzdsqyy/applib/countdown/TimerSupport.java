package com.wzdsqyy.applib.countdown;

/**
 * Created by Administrator on 2016/9/21.
 */

public interface TimerSupport{
    /**
     *
     * @return 毫秒
     */
    long countDownInterval();

    /**
     *
     * @return 结束时间毫秒
     */
    long getEndTime();
}
