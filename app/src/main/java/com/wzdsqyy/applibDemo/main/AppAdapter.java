package com.wzdsqyy.applibDemo.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.utils.CommonAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */

public class AppAdapter extends CommonAdapter<Bean> implements AdapterView.OnItemClickListener {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView item = (TextView) convertView;
        if (convertView == null) {
            item = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
        }
        item.setText(((Bean)getItem(position)).name);
        return item;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.getContext().startActivity(new Intent(view.getContext(),getBeans().get(position).clazz));
    }
}
