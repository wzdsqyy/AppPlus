package com.wzdsqyy.jsbridge;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient implements WebViewJavascriptBridge, JsCallBack {
    private BridgeErrorListener listener;//错误处理监听这
    private BridgeModel bridge;
    private WebView webView;
    Map<String, JsCallBack> jsCallBacks = new HashMap<>();
    Map<String, BridgeHandler> jsCalls = new HashMap<>();
    private BridgeHandler defaultHandler;

    public BridgeWebViewClient(@NonNull BridgeModel bridge) {
        this.bridge = bridge;
    }

    public void setDefaultHandler(BridgeHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public void onDestroy() {
        jsCallBacks.clear();
        jsCalls.clear();
    }

    /**
     * register handler,so that javascript can call it
     *
     * @param handlerName
     * @param handler
     */
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null) {
            jsCalls.put(handlerName, handler);
        }
    }


    private void handlerReturnData(String url) {
        Message prame = Utils.toMessage(url);
        if (prame != null) {
            if (!TextUtils.isEmpty(prame.getResponseId())) {
                JsCallBack callBack = jsCallBacks.get(prame.getHandlerName());
                if (callBack != null) {
                    callBack.onCallBack(prame.getData());
                    jsCallBacks.remove(prame.getHandlerName());
                }
            }
        }
    }


    private List<Message> startupMessage = new ArrayList<Message>();

    public List<Message> getStartupMessage() {
        return startupMessage;
    }

    public void setStartupMessage(List<Message> startupMessage) {
        this.startupMessage = startupMessage;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.webView = view;
    }

    @Override
    public void send(String data) {
        send(data, null);
    }

    @Override
    public void send(String data, JsCallBack responseCallback) {
        doSend(null, data, responseCallback);
    }

    private void doSend(String handlerName, String data, JsCallBack responseCallback) {
        Message m = new Message();
        if (!TextUtils.isEmpty(data)) {
            m.setData(data);
        }
        if (responseCallback != null) {
            String callbackStr = Utils.getCallBackId(m);
            jsCallBacks.put(callbackStr, responseCallback);
            m.setCallbackId(callbackStr);
        }
        if (!TextUtils.isEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }
        queueMessage(m);
    }

    private void queueMessage(Message m) {
        if (startupMessage != null) {
            startupMessage.add(m);
        } else {
            UiJavaCalljsTask task = new UiJavaCalljsTask(webView);
            task.dispatchMessage(m, bridge, listener);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
            if (url.startsWith(Utils.RETURN)) { // 如果是返回数据
                handlerReturnData(url.replace(Utils.RETURN, ""));
                return true;
            } else if (url.startsWith(Utils.CALL)) { //
                handlerCallData(url.replace(Utils.CALL, ""));
                return true;
            }
        } catch (Exception e) {
            Utils.error(listener, e);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    private void handlerCallData(String data) {
        Message prame = Utils.toMessage(data);
        if (prame != null) {
            if (!TextUtils.isEmpty(prame.getCallbackId())) {

            }


            BridgeHandler handler = jsCalls.get(prame.getHandlerName());
            if (handler != null) {
                handler.handler(prame.getData());
                jsCalls.remove(prame.getHandlerName());
            }
        }
    }

    void callJsMethod(String jscmd, JsCallBack returnCallback) {
        if (this.webView != null) {
            this.webView.loadUrl(jscmd);
            jsCallBacks.put(BridgeUtil.parseFunctionName(jscmd), returnCallback);
        }
    }

    void callJs() {
        callJsMethod(BridgeUtil.JS_FETCH_QUEUE_FROM_JAVA, this);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (getStartupMessage() != null) {
            for (Message m : getStartupMessage()) {
                UiJavaCalljsTask task = new UiJavaCalljsTask(view);
                task.dispatchMessage(m, bridge, listener);
            }
            setStartupMessage(null);
        }
    }

    @Override
    public void onCallBack(String data) {
        // deserializeMessage
        List<Message> list = null;
        try {
            list = Message.toArrayList(data);
            if (list == null || list.size() == 0) {
                return;
            }
        } catch (Exception e) {
            Utils.error(listener, e);
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Message m = list.get(i);
            String responseId = m.getResponseId();
            // 是否是response
            if (!TextUtils.isEmpty(responseId)) {
                JsCallBack function = jsCallBacks.get(responseId);
                String responseData = m.getResponseData();
                function.onCallBack(responseData);
                jsCallBacks.remove(responseId);
            } else {
                JsCallBack responseFunction = null;
                // if had callbackId
                final String callbackId = m.getCallbackId();
                if (!TextUtils.isEmpty(callbackId)) {
                    responseFunction = new JsCallBack() {
                        @Override
                        public void onCallBack(String data) {
                            Message responseMsg = new Message();
                            responseMsg.setResponseId(callbackId);
                            responseMsg.setResponseData(data);
                            queueMessage(responseMsg);
                        }
                    };
                } else {
                    responseFunction = new JsCallBack() {
                        @Override
                        public void onCallBack(String data) {
                            // do nothing
                        }
                    };
                }
                BridgeHandler handler;
                if (!TextUtils.isEmpty(m.getHandlerName())) {
                    handler = jsCalls.get(m.getHandlerName());
                } else {
                    handler = defaultHandler;
                }
                if (handler != null) {
                    handler.handler(m.getData());
                }
            }
        }
    }
}