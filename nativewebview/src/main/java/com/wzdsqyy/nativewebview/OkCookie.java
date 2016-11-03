package com.wzdsqyy.nativewebview;

import android.util.Log;
import android.webkit.CookieManager;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2016/11/3.
 */

public class OkCookie implements CookieJar {
    private static final String TAG = "OkCookie";
    CookieManager manager=CookieManager.getInstance();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Log.d(TAG, "saveFromResponse() called with: url = [" + url + "], cookies = [" + cookies + "]");
        manager.setCookie(url.toString(),cookies.toString());
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        manager.getCookie(url.toString());
        Log.d(TAG, "loadForRequest() called with: url = [" + url + "]");
        return null;
    }
}
