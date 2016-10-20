package com.wzdsqyy.applibDemo.lrulist;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wzdsqyy.applibDemo.R;

import static android.R.attr.startX;

/**
 * Created by Administrator on 2016/10/19.
 */

public class LooperView extends BaseAdapter implements View.OnTouchListener {
    private static final String TAG = "LooperView";
    private float downX;

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {


            convertView = new View(parent.getContext());
        }
        convertView.setBackgroundColor(getCloro(position));
        Log.d(TAG, "getView() called with: position = [" + position + "]");
        return convertView;
    }

    public int getCloro(int position) {
        switch (position) {
            case 0:
                return 0xffff0000;
            case 1:
                return 0xff00ff00;
            case 2:
                return 0xff0000ff;
        }
        return 0xff00ffff;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
        }
        return false;
    }
}
