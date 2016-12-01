package com.wzdsqyy.applibDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wzdsqyy.applibDemo.main.AppAdapter;
import com.wzdsqyy.applibDemo.main.MainHelper;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    AppAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.list_item);
        adapter=new AppAdapter();
        listView.setAdapter(adapter);
        adapter.setBeans(MainHelper.getActivityList());
        listView.setOnItemClickListener(adapter);












    }
}
