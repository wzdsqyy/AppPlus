package com.wzdsqyy.applibDemo.viewpager;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.viewpager.fragment.MyAdapter;

/**
 * Created by Administrator on 2016/10/13.
 */

public class ListViewPagerAdapter extends BaseAdapter{

    private int index;

    public ListViewPagerAdapter(int index) {
        this.index = index;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return "item"+position+" index:"+index;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewPager pager;
        if(convertView==null){
            pager= (ViewPager) LayoutInflater.from(parent.getContext()) .inflate(R.layout.item_viewpager,parent,false);
            pager.setAdapter(new MyAdapter(position%5+5));
        }else {
            pager= (ViewPager) convertView;
        }
        return pager;
    }
}
