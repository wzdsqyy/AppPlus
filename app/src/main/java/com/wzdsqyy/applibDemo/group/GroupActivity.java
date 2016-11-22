package com.wzdsqyy.applibDemo.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wzdsqyy.applibDemo.R;

public class GroupActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        recyclerView= (RecyclerView) findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
    }
}
