package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2016/9/23.
 */

public class Utils {
    public static final String SCHEMA="JsBridge://";
    public static final String CALL=SCHEMA+"call/";//JsBridge://call/{handlername}/{prama}/
    public static final String RETURN=SCHEMA+"back/";//JsBridge://back/{callid}/{prama}}


    public static final String JAVA_CALL_JS="javascript:WebViewJavascriptBridge._handleMessageFromNative('%s','%s');";//JsBridge://back/{callid}/{prama}}
    public static final String SPLIT="/";

    public static void error(BridgeErrorListener listener,Throwable ex){
        if(listener!=null){
            listener.onBridgeError(ex);
        }
    }

    public static String getCallBackId(Message message){
        return ""+System.currentTimeMillis()+message.hashCode();
    }
    public static Message toMessage(@NonNull String data){
        return toMessage(data,false);
    }

    public static Message toMessage(@NonNull String data,boolean isBack){
        Message message=new Message();
        String[] split = data.split(SPLIT);
        if(split!=null){
            if(split.length==1){
                message.setHandlerName(split[0]);
            }else if (split.length==2){
                message.setHandlerName(split[0]);
                message.setData(split[1]);
            }
        }
        return message;
    }
}
