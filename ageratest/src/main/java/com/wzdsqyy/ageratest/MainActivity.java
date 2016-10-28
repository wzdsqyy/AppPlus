package com.wzdsqyy.ageratest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Observable;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

public class MainActivity extends AppCompatActivity implements Updatable,Receiver<Result<String>>,Observable{

    private Repository<String> compile;
    MutableRepository<Result<String>> repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = Repositories.mutableRepository(Result.<String>absent());
    }

    @Override
    public void update() {

    }

    @Override
    public void accept(@NonNull Result<String> value) {
        if(value.succeeded()){

        }else {

        }
    }

    @Override
    public void addUpdatable(@NonNull Updatable updatable) {

    }

    @Override
    public void removeUpdatable(@NonNull Updatable updatable) {

    }
}
