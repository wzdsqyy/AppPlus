package com.wzdsqyy.applibDemo.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;

public class CountDownActivity extends AppCompatActivity{
    private TextView text;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        text= (TextView) findViewById(R.id.text);
        listView= (ListView) findViewById(R.id.list_item);
        AppAdapter adapter = new AppAdapter();
        listView.setAdapter(adapter);
    }
}
