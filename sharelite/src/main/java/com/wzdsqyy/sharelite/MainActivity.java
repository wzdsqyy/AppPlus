package com.wzdsqyy.sharelite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umeng.socialize.UMShareAPI;
import com.wzdsqyy.fragment.internal.ManagerProvider;

public class MainActivity extends AppCompatActivity {
    ManagerProvider provider=ManagerProvider.newInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UMShareAPI.init(this,null);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        provider.onPostResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        provider.onSaveInstanceState();
    }
}
