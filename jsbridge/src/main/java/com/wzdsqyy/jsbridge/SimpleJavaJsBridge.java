package com.wzdsqyy.jsbridge;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;


import com.wzdsqyy.jsbridge.annotation.InvokeJSInterface;
import com.wzdsqyy.jsbridge.annotation.JavaCallback4JS;
import com.wzdsqyy.jsbridge.annotation.JavaInterface4JS;

import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 该类是本库的核心类，看例子
 * <p>生成{@link SimpleJavaJsBridge}的实例：</p>
 * <pre>
 *     SimpleJavaJsBridge instance = new SimpleJavaJsBridge.Builder().addJavaInterface4JS(javaInterfaces4JS)
                                    .setWebView(mWebView)
                                    .setJSMethodName4Java("_JSBridge._handleMessageFromNative")
                                    .setProtocol("didiam://__QUEUE_MESSAGE__/?").create();
 *
 *      这样可以创建一个SimpleJavaJsBridge的实例，当然还可以设置其他参数，但是上面的几个参数是必须设定的，
 *      发送给对方的数据，我给起了个名字叫request（请求数据），
 *      request的格式是：
 *              <pre>
 *                  {
 *                      "handlerName":"test",
 *                      "callbackId":"c_111111",
 *                      "params":{
 *                          ....
 *                      }
 *                  }
 *              </pre>
 *      request里面的这三个关键的key值的名字是可以重新定义的，比如"handlerName"可以使用 new SimpleJavaJsBridge.Builder().setHandlerName("interfaceName")
 *      来进行自定义。
 *
 *      接收到对方的数据，我给起个名字叫response(响应数据),
 *      response的格式是:
 *              <pre>
 *                  {
 *                      "responseId":"iii",
 *                      "data":{
 *                          "status":"1",
 *                          "msg":"ok",
 *                          "values":{
 *                              ......
 *                          }
 *                      }
 *                  }
 *              </pre>
 *       同理response里面的"responseId","data","values"这三个关键的key值的名字也是可以调用SimpleJavaJsBridge.Builder进行自定义的
 * </pre>
 *
 * <p>调用js接口的例子:</p>
 * <pre>
 *     //声明一个调用js的interface
 *     interface IInvokeJS{
 *           //声明一个调用js的 exam4 的接口，该接口不需要传递参数
 *           :@InvokeJSInterface("exam4")
 *           void exam4(@ParamCallback IJavaCallback2JS iJavaCallback2JS);
 *     }
 *
 *     //开始调用js的接口
 *     IInvokeJs invokeJs = mSimpleJavaJsBridge.createInvokJSCommand(IInvokeJs.class);
 *     invokeJS.exam4(new IJavaCallback2JS{
 *         :@JavaCallback4JS
 *         public void callback(@ParamResponseStatus("status") String status){
 *
 *         }
 *     });
 *
 *     以上就是一个调用js的exam4接口的例子，其实该过程是模仿了retrofit的。这样做的好处是上层使用者完全不需要关心从json中解析数据这
 *     一繁琐的重复的体力劳动了
 *
 * </pre>
 *
 * Created by niuxiaowei on 16/6/15.
 */
public class SimpleJavaJsBridge {

     static final String TAG = SimpleJavaJsBridge.class.getSimpleName();

     static final String JAVASCRIPT = "javascript:";
    /**
     * java调用js的功能时，java会为js提供回调函数，但是不可能把回调函数传递给js，
     * 所以为回调函数提供一个唯一的id，
     */
     static int sUniqueCallbackId = 1;


    /**
     * 保证发送给js数据时在ui线程中执行
     */
     Handler mMainHandler = new Handler(Looper.getMainLooper());

    /**
     * 缓存java为js提供的接口
     */
     HashMap<String, MethodHandler> mJavaInterfaces4JSCache = new HashMap<>();

    /**
     * 缓存java为js提供搞的回调方法
     */
     HashMap<String, MethodHandler> mJavaCallbackMethods4JSCache = new HashMap<>();


    /*js为java敞开的唯一的一个可调用的方法，该方法接收一个字符串，字符串是json格式*/
     String mJSMethod4SendData2JS;
    /**
     * 协议的格式是:scheme+"://"+host+"?"
     */
     String mProtocol;
     WebView mWebView;
     SimpleJavaJSWebChromeClient mSimpleJavaJSWebChromeClient;

    /**
     * 是否是debug模式，debug模式可以把交互信息打出来
     */
     boolean mIsDebug;


    SimpleJavaJsBridge(@NonNull Builder builder) {
        Params.init(this);
        mWebView = builder.mWebView;
        mWebView.getSettings().setJavaScriptEnabled(true);
        mSimpleJavaJSWebChromeClient = new SimpleJavaJSWebChromeClient(builder.mWebChromeClient, this);
        mWebView.setWebChromeClient(mSimpleJavaJSWebChromeClient);
        saveJavaMethods4JS(builder.mJavaMethod4JS);
        RequestResponseBuilder.init(builder.mResponseIdName, builder.mResponseName, builder.mResponseValuesName, builder.mRequestInterfaceName, builder.mRequestCallbackIdName, builder.mRequestValuesName);
        mJSMethod4SendData2JS = builder.mJSMethodName4Java;
        mProtocol = builder.mProtocol;
    }


    /**
     * 生成调用js的命令，在调用js之前必须得调用该方法，该模式是模仿retrofit的
     * @param tClass 必须是一个interface
     * @param <T>
     * @return
     */
    public <T> T createInvokJSCommand(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass},new CallJsProxyHandler(this));
    }

    /**
     * 存储java为js提供的接口们
     *
     * @param javaMethods4JSes
     */
     void saveJavaMethods4JS(ArrayList javaMethods4JSes) {
        if(javaMethods4JSes==null){
            return;
        }
        for (int i = 0; i < javaMethods4JSes.size(); i++) {
            Object instance = javaMethods4JSes.get(i);
            if(instance==null){
                continue;
            }
            Class bridgeClass = instance.getClass(); //把java提供给js调用的接口放到json中
            Method[] allMethod = bridgeClass.getMethods();
            JavaInterface4JS annotation;
            for (Method method : allMethod) {
                annotation = method.getAnnotation(JavaInterface4JS.class);
                if(annotation==null){
                    continue;
                }
                MethodHandler methodHandler = MethodHandler.createMethodHandler(instance, method);//说明这是提供给js的接口
                if(methodHandler==null){
                    continue;
                }
                mJavaInterfaces4JSCache.put(annotation.value(), methodHandler);
            }
        }
    }

    /**
     * 生成唯一的回调id
     *
     * @return
     */
     static String generaUniqueCallbackId() {
        return ++sUniqueCallbackId + "_" + System.currentTimeMillis();
    }

     void sendRequest2JS(RequestResponseBuilder requst) {
        if(requst==null){
            return;
        }
        String callbackId = generaUniqueCallbackId();
        requst.setCallbackId(callbackId);
        if(requst.getCallback()==null){/*处理提供给js的回调方法*/
            return;
        }
        Class bridgeClass = requst.getCallback().getClass();
        Method[] allMethod = bridgeClass.getMethods();
        JavaCallback4JS javaCallback4JS;
        for (Method method : allMethod) {
            javaCallback4JS = method.getAnnotation(JavaCallback4JS.class);
            if(javaCallback4JS==null){
                continue;
            }
            mJavaCallbackMethods4JSCache.put(callbackId, MethodHandler.createMethodHandler(requst.getCallback(), method));
        }
        startSendData2JS(requst.toString());
    }

     void sendResponse2JS(RequestResponseBuilder response) {
        if(response==null)return;
        startSendData2JS(response.toString());
    }

    /**
     * 发送数据给js
     */
    public void sendData2JS(final RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null) {
            return;
        }
        if (requestResponseBuilder.isBuildRequest()) {
            sendRequest2JS(requestResponseBuilder);
        } else {
            sendResponse2JS(requestResponseBuilder);
        }
    }


    /**
     * 开始发送数据给js
     *
     * @param data
     */
     void startSendData2JS(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        data = String.format(mJSMethod4SendData2JS, data);
        final String finalData = data;
        if(mIsDebug){
            Log.i(TAG, "发送给js的数据:" + data );
        }
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(finalData);
            }
        });

    }

    /**
     * 解析从js传递过来的json数据
     *
     * @param json
     * @return true 代表可以解析当前数据，否则不可以解析
     */
    boolean parseJsonFromJs(@NonNull String json) {
        if(!json.startsWith(mProtocol)){
            return false;
        }
        json = json.substring(json.indexOf(mProtocol, 0) + mProtocol.length());
        if(mIsDebug){
            Log.i(TAG, "收到js发送过来的数据:" + json );
        }
        try {
            JSONObject data = new JSONObject(json);
            if (data == null) {
                return false;
            }
            invokeJavaMethod(RequestResponseBuilder.create(data));/*开始调用java的方法*/
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 开始调用java的方法，
     *
     * @param requestResponseBuilder
     */
     void invokeJavaMethod(@NonNull RequestResponseBuilder requestResponseBuilder) {
        /*说明这是响应数据*/
        if (!requestResponseBuilder.isBuildRequest()) {
            MethodHandler methodHandler = mJavaCallbackMethods4JSCache.remove(requestResponseBuilder.getResponseId());
            if (methodHandler == null) {
                Log.e(TAG, "回调方法不存在");
                return;
            }
            methodHandler.invoke(requestResponseBuilder);
        } else {
            /*说明是js请求java的请求数据*/
            MethodHandler methodHandler = mJavaInterfaces4JSCache.get(requestResponseBuilder.getInterfaceName());
            if (methodHandler != null) {
                methodHandler.invoke(requestResponseBuilder);
            } else {
                Log.e(TAG, "所调用的接口不存在");

                RequestResponseBuilder errorResponse = new RequestResponseBuilder(false);
                errorResponse.setResponseId(requestResponseBuilder.getCallbackId());
                errorResponse.putResponseStatus("errmsg","所调用的接口不存在");
                sendResponse2JS(errorResponse);
            }

        }
    }
}
