package com.wzdsqyy.weiman.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.agera.Observable;
import com.google.android.agera.Observables;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import com.google.android.agera.UpdateDispatcher;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.weiman.bean.ComicsItem;
import com.wzdsqyy.weiman.data.model.ComicsModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Updatable, Observable {
    Repository<Result<List<ComicsItem>>> list;
    private UpdateDispatcher dispatcher= Observables.updateDispatcher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=ComicsModel.getList("1", ComicsModel.getType(ComicsModel.getNames().get(1)),this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        list.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        list.removeUpdatable(this);
    }

    @Override
    public void update() {
        Result<List<ComicsItem>> result = list.get();
    }

    @Override
    public void addUpdatable(@NonNull Updatable updatable) {
        dispatcher.addUpdatable(updatable);
    }

    @Override
    public void removeUpdatable(@NonNull Updatable updatable) {
        dispatcher.removeUpdatable(updatable);
    }
}
