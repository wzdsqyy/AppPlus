package com.wzdsqyy.jsbridge;

/**
 * 错误回调，JsBridge通讯中发生任何错误，回调该方法
 */
public interface BridgeErrorListener {
    void onBridgeError(Throwable ex);
}
