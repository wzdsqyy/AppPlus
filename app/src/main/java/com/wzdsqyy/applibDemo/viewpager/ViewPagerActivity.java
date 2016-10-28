package com.wzdsqyy.applibDemo.viewpager;

import android.graphics.BitmapShader;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.fragment.MyAdapter;
import com.wzdsqyy.utils.ElasticTouchListener;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager pager;
    private MyAdapter adapter=new MyAdapter();
    private boolean v=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        pager= (ViewPager) findViewById(R.id.main_viewpager);
        pager.setAdapter(adapter);
        pager.setOnTouchListener(new ElasticTouchListener());
    }

    public void chuizhi(View view){






        BitmapShader shader;














    }
}
