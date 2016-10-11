package com.wzdsqyy.applib.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashSet;

import javax.net.ssl.SSLHandshakeException;

public class HttpRetry {

    public static final int RETRY_MAX = 3;
    // 重试休息的时间
    private static final int RETRY_SLEEP_TIME_MILLIS = 1000;
    // 网络异常，继续
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();
    // 用户异常，不继续（如，用户中断线程）
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();

    static {
        // 以下异常不需要重试，这样异常都是用于造成或者是一些重试也无效的异常
        exceptionWhitelist.add(UnknownHostException.class);// host出了问题，一般是由于网络故障
        exceptionWhitelist.add(SocketException.class);// Socket问题，一般是由于网络故障
        // 以下异常可以重试
        exceptionBlacklist.add(InterruptedIOException.class);// 连接中断，一般是由于连接超时引起
        exceptionBlacklist.add(SSLHandshakeException.class);// SSL握手失败
    }

    private static HashSet<Class<?>> blackList = new HashSet<Class<?>>();

    static {
        blackList.add(MalformedURLException.class);
        blackList.add(URISyntaxException.class);
        blackList.add(NoRouteToHostException.class);
        blackList.add(PortUnreachableException.class);
        blackList.add(ProtocolException.class);
        blackList.add(NullPointerException.class);
        blackList.add(FileNotFoundException.class);
        blackList.add(UnknownHostException.class);
        blackList.add(IllegalArgumentException.class);
    }

    private final int maxRetries;

    public HttpRetry(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public HttpRetry() {
        maxRetries = RETRY_MAX;
    }

    public boolean retry(IOException ex, int count) {
        if (count > maxRetries ) {
            return false;
        }

        if (blackList.contains(ex.getClass())) {
            return false;
        }

        return true;
    }

    public int getRetryMaxCount() {
        return maxRetries;
    }
}