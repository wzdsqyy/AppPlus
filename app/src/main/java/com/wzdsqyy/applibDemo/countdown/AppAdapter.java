package com.wzdsqyy.applibDemo.countdown;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.wzdsqyy.applib.countdown.CountDownHelper;
import com.wzdsqyy.applib.countdown.CountDownListener;
import com.wzdsqyy.applib.countdown.ListTimerSupport;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.applibDemo.utils.CommonAdapter;

import java.util.Random;

/**
 * Created by Administrator on 2016/9/20.
 */

public class AppAdapter extends CommonAdapter implements AbsListView.RecyclerListener{

    private CountDownHelper helper;
    private Random random=new Random();
    private SparseArray<CountDownListener> listeners=new SparseArray<>();

    public AppAdapter(@NonNull CountDownHelper helper) {
        this.helper = helper;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(final int position) {
        return new ListTimerSupport() {
            @Override
            public int getTimerSupportPossion() {
                return position;
            }

            @Override
            public int countDownInterval() {
                return 1;
            }

            @Override
            public int getTotalTime() {
                return random.nextInt(20)+5;
            }
        };
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
        ListTimerSupport listTimerSupport = (ListTimerSupport) getItem(position);
        holder.setTimerSupport(listTimerSupport);
        helper.bindListCountDownListener(listTimerSupport,holder);
        convertView=item;
        Log.d("555", "getView: "+listeners.toString());
        return convertView;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        ViewHolder holder= (ViewHolder) view.getTag();
        helper.clearListener(holder.getTimerSupport());
    }
}
