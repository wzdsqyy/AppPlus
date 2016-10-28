package com.wzdsqyy.applibDemo.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Administrator on 2016/10/11.
 */

public class ProxyActivity extends Activity {

    public static <T extends Activity> void start(Context context, Class<T> clazz) {
        Intent starter = new Intent(context, clazz);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
