package com.wzdsqyy.applibDemo.recreate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wzdsqyy.applibDemo.R;

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
}
