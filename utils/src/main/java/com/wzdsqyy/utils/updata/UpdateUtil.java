/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wzdsqyy.utils.updata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.wzdsqyy.utils.AppUtils;
import com.wzdsqyy.utils.IOUtil;
import com.wzdsqyy.utils.Md5;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class UpdateUtil {
    private static final String TAG = "ezy.update";
    private static final String PREFS = "ezy.update.prefs";
    private static final String PREFS_IGNORE = "ezy.update.prefs.ignore";
    private static final String PREFS_UPDATE = "ezy.update.prefs.update";

    static boolean DEBUG = true;

    public static void log(String content) {
        if (DEBUG) {
            Log.i(TAG, content);
        }
    }

    public static void clean(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, 0);
        File file = new File(context.getExternalCacheDir(), sp.getString(PREFS_UPDATE, ""));
        if (file.exists()) {
            file.delete();
        }
        sp.edit().clear().apply();
    }

    public static void setUpdate(Context context, String md5) {
        if (TextUtils.isEmpty(md5)) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(PREFS, 0);
        String old = sp.getString(PREFS_UPDATE, "");
        if (md5.equals(old)) {
            return;
        }
        File oldFile = new File(context.getExternalCacheDir(), old);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        sp.edit().putString(PREFS_UPDATE, md5).apply();
        File file = new File(context.getExternalCacheDir(), md5);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    public static void setIgnore(Context context, String md5) {
        context.getSharedPreferences(PREFS, 0).edit().putString(PREFS_IGNORE, md5).apply();
    }

    public static boolean isIgnore(Context context, String md5) {
        return !TextUtils.isEmpty(md5) && md5.equals(context.getSharedPreferences(PREFS, 0).getString(PREFS_IGNORE, ""));
    }

    public static void install(Context context) {
        String md5 = context.getSharedPreferences(PREFS, 0).getString(PREFS_UPDATE, "");
        File file = new File(context.getExternalCacheDir(), md5);
        if (UpdateUtil.verify(file)) {
            AppUtils.install(context, file);
        }
    }

    public static boolean verify(File file) {
        if (!file.exists()) {
            return false;
        }
        String md5 = Md5.md5(file);
        return md5 != null && md5.equalsIgnoreCase(file.getName());
    }


    public static String toCheckUrl(Context context, String url, String channel) {
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append(url.indexOf("?") < 0 ? "?" : "&");
        builder.append("package=");
        builder.append(context.getPackageName());
        builder.append("&version=");
        builder.append(AppUtils.getVerCode(context));
        builder.append("&channel=");
        builder.append(channel);
        return builder.toString();
    }



    public static boolean isDebuggable(Context context) {
        try {
            return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }


    public static String readString(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            output.flush();
        } finally {
            IOUtil.closeQuietly(input);
            IOUtil.closeQuietly(output);
        }
        return output.toString("UTF-8");
    }
}