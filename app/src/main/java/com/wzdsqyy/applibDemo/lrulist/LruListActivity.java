package com.wzdsqyy.applibDemo.lrulist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.wzdsqyy.applib.utils.LruList;
import com.wzdsqyy.applibDemo.R;

public class LruListActivity extends AppCompatActivity {

    EditText text;
    LruList<String> lruList=new LruList<>();
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_list);
        text= (EditText) findViewById(R.id.input);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        listView= (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
    }

    public void reSetNum(View view) {
        String string = text.getEditableText().toString();
        if(!TextUtils.isEmpty(string)&&TextUtils.isDigitsOnly(string)){
            lruList.setMaxCount(Integer.parseInt(string));
        }
        refershList(null);
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
