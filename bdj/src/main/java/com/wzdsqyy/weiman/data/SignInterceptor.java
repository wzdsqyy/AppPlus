package com.wzdsqyy.weiman.data;

import android.text.TextUtils;

import com.wzdsqyy.utils.Md5;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Qiuyy on 2016/8/11.
 */
public class SignInterceptor implements Interceptor {
    private final String appid, secret;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    private boolean isGzip = true;

    public SignInterceptor(String appid, String secret) {
        this.secret = secret;
        this.appid = appid;
    }

    public void setIsGzip(boolean isGzip) {
        this.isGzip = isGzip;
    }

    public SignInterceptor() {
        this("19058", "55f4595a7c494de2b6e198d386b65ff8");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl.Builder builder = request.url().newBuilder()
                .addQueryParameter("showapi_appid", appid)
                .addQueryParameter("showapi_timestamp", format.format(new Date()))
                .addQueryParameter("showapi_sign_method", "md5")
                .addQueryParameter("showapi_res_gzip", isGzip ? "1" : "0");
        HttpUrl temp = builder.build();
        Set<String> keys = temp.queryParameterNames();
        ArrayList<String> list = new ArrayList<String>(keys.size());
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            StringBuilder itemList = new StringBuilder();
            String key = iterator.next();
            String value = temp.queryParameter(key);
            if (!TextUtils.isEmpty(value)) {
                list.add(itemList.append(key).append(value).toString());
            }
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        StringBuilder signBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (!TextUtils.isEmpty(s)) {
                signBuilder.append(s);
            }
        }
        signBuilder.append(secret);
        String sign = Md5.md5(signBuilder.toString());
        HttpUrl url = builder.addQueryParameter("showapi_sign", sign).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
