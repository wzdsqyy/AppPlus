package com.wzdsqyy.applibDemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.style.URLSpan;
import android.view.View;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.fragment.ContentPage;
import com.wzdsqyy.fragment.NavManager;
import com.wzdsqyy.fragment.PageManager;

public class OneActivity extends AppCompatActivity implements ContentPage{

    NavManager manager;
    int index=0;
    private static final int TEST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        manager = PageManager.attachNav(this);
        MainFragment fragment= (MainFragment) getPageFragmentManager().findFragmentByTag("MainFragment");
        if(fragment==null){
            fragment=MainFragment.newInstance(index);
            index++;
        }
        manager.showPage(fragment,"MainFragment");
    }

    public void nextPage(View view){
        index++;
        MainFragment fragment= MainFragment.newInstance(index);
        manager.pushPage(fragment,null);
    }

    public void prePage(View view){
        if(manager.getBackStackCount()>0){
            manager.popPage();
            index--;
        }
    }

    public void saveState(View v){
        SecondActivity.start(this,TEST);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        manager.getSaveState().onSaveInstanceState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TEST){
            manager.pushPage(TestFragment.newInstance(),"TestFragment");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        manager.getSaveState().onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public int getContentId() {
        return R.id.activity_one;
    }

    @Override
    public FragmentManager getPageFragmentManager() {
        return getSupportFragmentManager();
    }
}
