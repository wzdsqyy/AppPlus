package com.wzdsqyy.jsbridgedemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.wzdsqyy.jsbridgedemo.fragment.WebViewFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        String url = "file:///android_asset/bridge_demo.html";
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.webview_layout);
        if (fragment == null || !(fragment instanceof WebViewFragment)) {
            fragment = (WebViewFragment.createWebViewFragment(url));
        }
        fm.beginTransaction().replace(R.id.webview_layout, fragment).commit();
    }
}
