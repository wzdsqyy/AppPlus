package com.wzdsqyy.applib.utils;

import okhttp3.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 调用Java原生的网络请求工具类
 */
public class HttpUtils {
    private static final int TIMEOUT_IN_MILLIONS = 5000;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 向指定 URL 发送POST方法的请求,调用Java的原生的接口
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 json 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        URL realUrl;
        try {
            realUrl = new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
        HttpURLConnection conn = null;
        if (realUrl != null) {
            try {
                conn = (HttpURLConnection) realUrl.openConnection();// 打开和URL之间的连接
            } catch (IOException e) {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } else {
            return null;
        }
        StringBuffer result = null;
        try {
            if (conn != null) {
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "utf-8");
                conn.setUseCaches(false);
            } else {
                return null;
            }
        } catch (ProtocolException e) {
            if (conn != null) {
                conn.disconnect();
            }
            return null;
        }
        /*发送POST请求必须设置如下两行*/
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
        conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
        OutputStream outputStream = null;
        if (param != null && !param.trim().equals("")) {
            try {
                outputStream = conn.getOutputStream();
            } catch (IOException e) {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            PrintWriter out;
            if (outputStream != null) {
                out = new PrintWriter(outputStream);// 获取URLConnection对象对应的输出流
                out.print(param);// 发送请求参数
                out.flush(); // flush输出流的缓冲
            }
        } else {
            return null;
        }
        BufferedReader in;
        InputStream netInput = null;
        try {
            netInput = conn.getInputStream();
        } catch (IOException e) {
            conn.disconnect();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e2) {
                }
            }
        }
        in = new BufferedReader(new InputStreamReader(netInput));// 定义BufferedReader输入流来读取URL的响应
        String line;
        try {
            line = in.readLine();
            result = new StringBuffer();
            while (line != null) {
                result.append(line);
                line = in.readLine();
            }
        } catch (IOException e) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (netInput != null) {
                try {
                    netInput.close();
                } catch (IOException e) {
                }
            }
            if (result != null) {
                return result.toString();
            } else {
                return null;
            }
        }

    }
}
