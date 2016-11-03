package com.wzdsqyy.applibDemo.MutiItemList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/29.
 */

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnLayoutChangeListener {
    private static final String TAG = "TextViewHolder";
    TextView tv;
    public TextViewHolder(View itemView) {
        super(itemView);
        tv= (TextView) itemView;
        tv.addOnLayoutChangeListener(this);
    }

    public void setText(String text) {
        tv.setText(text);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.d(TAG, "onLayoutChange() called with: v = [" + v + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "], oldLeft = [" + oldLeft + "], oldTop = [" + oldTop + "], oldRight = [" + oldRight + "], oldBottom = [" + oldBottom + "]");
    }
}
