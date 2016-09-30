package com.wzdsqyy.ageratest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.agera.Receiver;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;

public class MainActivity extends AppCompatActivity implements Updatable,Receiver<String> {

    private Repository<String> compile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void update() {
    }

    @Override
    public void accept(@NonNull String value) {

    }
}
