package com.wzdsqyy.applibDemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wzdsqyy.applibDemo.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
    }

    public void finishActivity(View v){
        setResult(Activity.RESULT_OK);
        finish();
    }

    public static void start(Activity a, int code) {
        Intent starter = new Intent(a, SecondActivity.class);
        a.startActivityForResult(starter,code);
    }
}
