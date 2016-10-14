package com.wzdsqyy.applibDemo.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.wzdsqyy.applib.ui.nested.NestedHelper;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.fragment.ListAdapter;

public class NestedTwoListViewActivity extends AppCompatActivity {

    private ListView list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_two_list_view);
        list1= (ListView) findViewById(R.id.lv_listview_1);
        list1.setAdapter(new ListAdapter(1));
        NestedHelper.newInstance(this,list1);
    }
}
