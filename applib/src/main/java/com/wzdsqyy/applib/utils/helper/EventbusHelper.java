package com.wzdsqyy.applib.utils.helper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

/**
 * Created by Administrator on 2016/9/30.
 */

public class EventbusHelper {
    public static EventBusBuilder init(){
       return EventBus.builder().executorService(AsyncTaskFixedHelper.getHelper().getExecutor());
    }
}
