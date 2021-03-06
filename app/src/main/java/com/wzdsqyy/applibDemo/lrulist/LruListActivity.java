package com.wzdsqyy.applibDemo.lrulist;

import android.app.Activity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.commonview.SwipeBackHelper;
import com.wzdsqyy.commonview.SwipeBackLayout;
import com.wzdsqyy.utils.LruList;

import java.util.List;

public class LruListActivity extends AppCompatActivity {

    private static final String TAG = "LruListActivity";
    EditText text;
    LruList<String> lruList=new LruList<>();
    ListView listView;
    ArrayAdapter<String> adapter;
    AdapterViewFlipper flipper;
    private FragmentTransaction mCurTransaction;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_list);
        SwipeBackHelper.newInstance().attach(this).setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        text= (EditText) findViewById(R.id.input);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        listView= (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        flipper= (AdapterViewFlipper) findViewById(R.id.view_looper);
        LooperView  view= new LooperView();
        flipper.setAdapter(view);
        test(this);
    }

    public void reSetNum(View view) {
        String string = text.getEditableText().toString();
        if(!TextUtils.isEmpty(string)&&TextUtils.isDigitsOnly(string)&&string.length()<5){
            lruList.setMaxCount(Integer.parseInt(string));
        }
        refershList(null);
    }

    public void test(LruListActivity activity){
        Log.d(TAG, "test() called with: LruListActivity = [" + activity + "]");
    }

    public void test(Activity activity){
        Log.d(TAG, "test() called with: activity = [" + activity + "]");



    }


    public void test(Object object){
        Log.d(TAG, "test() called with: object = [" + object + "]");
    }

    public void refershList(View view) {
        adapter.clear();
        adapter.addAll(lruList.getAll());
    }

    public void addString(View view) {
        String string = text.getEditableText().toString();
        if(!TextUtils.isEmpty(string)){
            lruList.add(string);
        }
        refershList(null);
    }
}
