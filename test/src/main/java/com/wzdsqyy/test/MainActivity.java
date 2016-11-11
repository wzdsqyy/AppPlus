package com.wzdsqyy.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.agera.Updatable;

public class MainActivity extends AppCompatActivity implements Updatable {
    private static final String TAG = "MainActivity";
    MainViewModel model;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title= (TextView) findViewById(R.id.text);
        model=new MainViewModel(this);
    }

    @Override
    public void update() {
        String s = model.getHello().get();
        title.setText(s);
    }

    public void updataData(View view){
        model.refreshData();
    }
}
