package com.wzdsqyy.applibDemo.countdown;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.utils.CommonAdapter;
import com.wzdsqyy.utils.countdown.TimerSupport;

import java.util.Random;

/**
 * Created by Administrator on 2016/9/20.
 */

public class AppAdapter extends CommonAdapter{

    private Random random=new Random();
    private SparseArray<TimerSupport> tasks=new SparseArray<>();

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        TimerSupport task = tasks.get(position);
        if(task==null){
            task=new TimerSupport() {

                @Override
                public boolean isFinish() {
                    return false;
                }
            };
            tasks.put(position,task);
        }
        return task;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView item = (TextView) convertView;
        ViewHolder holder;
        if (item == null) {
            item = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
            holder=new ViewHolder(item);
            item.setTag(holder);
        }else {
            holder= (ViewHolder) item.getTag();
        }
        convertView=item;
        return convertView;
    }
}
