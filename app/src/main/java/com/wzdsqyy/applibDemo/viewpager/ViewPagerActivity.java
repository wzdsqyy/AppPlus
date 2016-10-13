package com.wzdsqyy.applibDemo.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wzdsqyy.applib.ui.weiget.VerticalViewPager;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.fragment.MyAdapter;

public class ViewPagerActivity extends AppCompatActivity {

    private VerticalViewPager pager;
    private MyAdapter adapter=new MyAdapter();
    private boolean v=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        pager= (VerticalViewPager) findViewById(R.id.main_viewpager);
        pager.setAdapter(adapter);
    }

    public void chuizhi(View view){
        v=!v;
        pager.setVertical(v);
        adapter.notifyDataSetChanged();
    }
}
