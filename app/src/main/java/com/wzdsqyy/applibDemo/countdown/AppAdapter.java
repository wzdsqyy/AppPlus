package com.wzdsqyy.applibDemo.countdown;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;


import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.utils.CommonAdapter;
import com.wzdsqyy.utils.countdown.CountDownHelper;
import com.wzdsqyy.utils.countdown.TimerSupport;

import java.util.Random;

/**
 * Created by Administrator on 2016/9/20.
 */

public class AppAdapter extends CommonAdapter implements AbsListView.OnScrollListener{

    private final CountDownHelper helper;
    private Random random=new Random();
    private SparseArray<TimerSupport> tasks=new SparseArray<>();

    public AppAdapter() {
        helper=new CountDownHelper(1000);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(final int position) {
        TimerSupport task = tasks.get(position);
        if(task==null){
            task=new TimerSupport() {
                long end=random.nextInt(10)*1000+15*1000+System.currentTimeMillis();
                @Override
                public boolean isFinish() {
                    return System.currentTimeMillis()-end>0;
                }

                @Override
                public long endTime() {
                    return end;
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
            helper.addCountDownListener(holder);
            item.setTag(holder);
        }else {
            holder= (ViewHolder) item.getTag();
        }
        holder.setSupport((TimerSupport) getItem(position));
        convertView=item;
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(AbsListView.OnScrollListener.SCROLL_STATE_FLING==scrollState){
            helper.stop();
        }else {
            helper.start();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
