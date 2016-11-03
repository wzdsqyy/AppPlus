package com.wzdsqyy.fragment.internal;

import android.support.v4.app.FragmentTransaction;

import com.wzdsqyy.fragment.SaveState;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/2.
 */
class SaveStateImpl implements SaveState {

    ArrayList<Runnable> tasks;
    public boolean commit=true;

    public ArrayList<Runnable> getTasks() {
        if(tasks==null){
            tasks=new ArrayList<>();
        }
        return tasks;
    }

    @Override
    public void onSaveInstanceState() {
        commit=false;
    }

    @Override
    public void onPostResume() {
        commit=true;
        if(tasks==null||tasks.size()==0){
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).run();
        }
        tasks.clear();
    }

    @Override
    public void commit(FragmentTransaction transaction) {
        if(commit){
            transaction.commit();
        }else {
            getTasks().add(new StateTask(transaction,false));
        }
    }

    @Override
    public void commitNow(FragmentTransaction transaction) {
        if(commit){
            transaction.commitNow();
        }else {
            getTasks().add(new StateTask(transaction,true));
        }
    }
}
