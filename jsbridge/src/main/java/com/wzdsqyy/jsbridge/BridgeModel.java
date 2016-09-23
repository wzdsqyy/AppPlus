package com.wzdsqyy.jsbridge;

/**
 * Created by Administrator on 2016/9/23.
 */

public interface BridgeModel {
    /**
     * 返回WebView load(String data) 调用的字符串
     *
     * @param data 数据
     * @return
     */
    String jsHandlerFromNative(String data);

    String jsHandlerFetchQuene();
}
