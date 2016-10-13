package com.wzdsqyy.applibDemo.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.wzdsqyy.applibDemo.R;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView= (ListView) findViewById(R.id.list_item);
        listView.setAdapter(new ListViewPagerAdapter(1));
    }
}
