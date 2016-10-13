package com.wzdsqyy.applibDemo.viewpager.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;

/**
 * Created by Administrator on 2016/10/13.
 */

public class ListAdapter extends BaseAdapter{

    private int index;

    public ListAdapter(int index) {
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
        TextView textView;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()) .inflate(android.R.layout.simple_list_item_1,parent,false);
        }
        textView= (TextView) convertView;
        textView.setText(getItem(position).toString());
        return textView;
    }
}
