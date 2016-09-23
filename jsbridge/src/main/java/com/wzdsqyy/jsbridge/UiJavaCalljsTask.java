package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/9/23.
 */

class UiJavaCalljsTask implements Runnable{
    private WebView view;
    private String javascriptCommand;

    public UiJavaCalljsTask(@NonNull WebView view) {
        this.view = view;
    }

    void dispatchMessage(Message m,@NonNull BridgeModel bridge, BridgeErrorListener listener) {
        try {
            if (m != null) {
                String messageJson = m.toJson();
                messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
                messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
                javascriptCommand = bridge.jsHandlerFromNative(messageJson);
            } else {
                javascriptCommand = bridge.jsHandlerFetchQuene();
            }
            view.getHandler().postAtFrontOfQueue(this);
        } catch (Exception e) {
            Utils.error(listener,e);
        }
    }

    @Override
    public void run() {
        view.loadUrl(javascriptCommand);
    }
}
