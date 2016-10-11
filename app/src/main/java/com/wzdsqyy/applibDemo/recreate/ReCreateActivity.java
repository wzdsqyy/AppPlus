package com.wzdsqyy.applibDemo.recreate;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.wzdsqyy.applibDemo.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReCreateActivity extends AppCompatActivity {

    ReCreteFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_create);
        if(savedInstanceState==null){
            fragment=new ReCreteFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void callback(String result) throws UnsupportedEncodingException {
        WebView webView = null;
        webView.loadUrl("javascript:_javaCallJavacript('"+ URLEncoder.encode(result,"utf-8")+"')");
    }
}
