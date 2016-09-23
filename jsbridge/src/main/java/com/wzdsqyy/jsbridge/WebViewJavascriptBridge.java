package com.wzdsqyy.jsbridge;


public interface WebViewJavascriptBridge {
	public void send(String data);
	public void send(String data, JsCallBack responseCallback);
}
