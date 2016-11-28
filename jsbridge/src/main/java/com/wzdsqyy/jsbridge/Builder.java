package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;

/**
 * 生成SimpleJavaJsBridge的实例
 */
public class Builder {

    String mResponseName;
    String mResponseValuesName;
    String mResponseIdName;


    String mRequestInterfaceName;
    String mRequestCallbackIdName;
    String mRequestValuesName;

    /*js为java敞开的唯一的一个可调用的方法，该方法接收一个字符串，字符串是json格式*/
    String mJSMethodName4Java;
    String mProtocol;


    WebChromeClient mWebChromeClient;
    ArrayList mJavaMethod4JS;
    WebView mWebView;
    JsonParser mParser;

    /**
     * 是否是debug模式
     */
    boolean mIsDebug = true;

    public Builder() {

    }

    public Builder setParser(JsonParser mParser) {
        this.mParser = mParser;
        return this;
    }

    /**
     * debug模式下，可以把交互信息打印出来
     *
     * @param debug
     * @return
     */
    public Builder setDebug(boolean debug) {
        mIsDebug = debug;
        return this;
    }

    /**
     * <pre>
     *  response格式：
     *  {
     *      "responseId":"iii",
     *      "data":{
     *          "status":"1",
     *          "msg":"ok",
     *          "values":{
     *              ......
     *          }
     *      }
     *  }
     *  responseId 代表request中的callbackId
     *  data       代表响应的数据
     *  status     代表响应状态
     *  msg        代表响应状态对应的消息
     *  values     代表响应数据包含的值
     *  </pre>
     * <p>
     * <pre>
     *      responseName的默认名字是"data"，可以对这个名字进行设置
     *  </pre>
     *
     * @param responseName
     * @return
     */
    public Builder setResponseName(String responseName) {
        mResponseName = responseName;
        return this;
    }

    /**
     * responseValuesName的默认名字是"values"，可以对这个名字进行设置
     *
     * @param responseValuesName
     * @return
     * @see #setResponseName(String)
     */
    public Builder setResponseValuesName(String responseValuesName) {
        mResponseValuesName = responseValuesName;
        return this;
    }

    /**
     * response中responseIdName的默认名字是"responseId"，可以对起进行设置
     *
     * @param responseIdName
     * @return
     * @see #setResponseName(String)
     */
    public Builder setResponseIdName(String responseIdName) {
        mResponseIdName = responseIdName;
        return this;
    }

    /**
     * <pre>
     *    {
     *      "handlerName":"test",
     *      "callbackId":"c_111111",
     *      "params":{
     *          ....
     *      }
     *    }
     *
     *    hanlerName 代表java与js之间给对方暴漏的接口的名称，
     *    callbackId 代表对方在发起请求时，会为回调方法生产一个唯一的id值，它就代表这个唯一的id值
     *    params     代表传递的数据
     *  </pre>
     * <pre>
     *      requestInterfaceName的默认值是"handlerName",可以进行设置它
     *  </pre>
     *
     * @param requestInterfaceName
     * @return
     */
    public Builder setRequestInterfaceName(String requestInterfaceName) {
        mRequestInterfaceName = requestInterfaceName;
        return this;
    }

    /**
     * 同理requestCallbackIdName的默认值是"callbackId",可以对它进行设置
     *
     * @param requestCallbackIdName
     * @return
     * @see #setRequestInterfaceName(String)
     */
    public Builder setRequestCallbackIdName(String requestCallbackIdName) {
        mRequestCallbackIdName = requestCallbackIdName;
        return this;
    }

    /**
     * 同理requestValuesName的默认值是"params",可以对它进行设置
     *
     * @param requestValuesName
     * @return
     * @see #setRequestInterfaceName(String)
     */
    public Builder setRequestValuesName(String requestValuesName) {
        mRequestValuesName = requestValuesName;
        return this;
    }

    /**
     * 设置js为java暴漏的方法的名字，只需要提供方法名字即可，具体的关于"()"和参数不需要提供，因为该方法接收的是一个json字符串
     *
     * @param JSMethodName 方法名字 比如：handleMsgFromJava
     * @return
     */
    public Builder setJSMethodName4Java(String JSMethodName) {
        mJSMethodName4Java = JSMethodName;
        if (!TextUtils.isEmpty(mJSMethodName4Java) && !mJSMethodName4Java.startsWith(SimpleJavaJsBridge.JAVASCRIPT)) {
            mJSMethodName4Java = SimpleJavaJsBridge.JAVASCRIPT + mJSMethodName4Java;
            if (!mJSMethodName4Java.contains("%s")) {
                mJSMethodName4Java = mJSMethodName4Java + "(%s)";
            }
        }
        return this;
    }

    /**
     * 设置协议，协议格式：scheme://host?，协议是必须进行设置的，否则报错
     *
     * @param scheme 比如 file或http等
     * @param host
     * @return
     */
    public Builder setProtocol(String scheme, String host) {
        if (TextUtils.isEmpty(scheme) || TextUtils.isEmpty(host)) {
            return this;
        }
        mProtocol = scheme + "://" + host + "?";
        return this;
    }

    public Builder setWebChromeClient(WebChromeClient webChromeClient) {
        mWebChromeClient = webChromeClient;
        return this;
    }

    /**
     * 添加java提供给js的接口
     *
     * @param javaMethod4JS
     * @return
     */
    public Builder addJavaInterface4JS(@NonNull Object javaMethod4JS) {
        if (mJavaMethod4JS == null) {
            mJavaMethod4JS = new ArrayList();
        }
        mJavaMethod4JS.add(javaMethod4JS);
        return this;
    }

    /**
     * 必须进行设置
     *
     * @param webView
     * @return
     */
    public Builder setWebView(WebView webView) {
        mWebView = webView;
        return this;
    }

    public SimpleJavaJsBridge create() {
        /*检查协议是否设置，并设置正确了*/
        Utils.checkProtocol(mProtocol);
        Utils.checkJSMethod(mJSMethodName4Java);
        if (mWebView == null) {
            throw new IllegalArgumentException("必须调用 setWebView(WebView) 方法设置Webview");
        }
        return new SimpleJavaJsBridge(this);
    }
}
