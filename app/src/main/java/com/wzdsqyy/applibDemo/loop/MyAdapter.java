package com.wzdsqyy.applibDemo.loop;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzdsqyy.applib.ui.banner.LoopAdapter;
import com.wzdsqyy.applibDemo.R;

/**
 * Created by Administrator on 2016/10/13.
 */

public class MyAdapter extends LoopAdapter{
    private int count;

    public MyAdapter(@NonNull ViewPager pager, int count) {
        this.count = count;
        setupPager(pager);
    }

    @Override
    public int getRealCount() {
        return count;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        TextView imageView=new TextView(container.getContext());
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
        imageView.setText("position"+position);
        return imageView;
    }
}
