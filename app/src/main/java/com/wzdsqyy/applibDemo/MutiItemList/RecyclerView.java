package com.wzdsqyy.applibDemo.MutiItemList;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/24.
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView {

    private static final String TAG = "RecyclerView";
    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void scrollBy(int x, int y) {
        Log.d(TAG, "scrollBy() called with: x = [" + x + "], y = [" + y + "]");
        super.scrollBy(x, y);
    }
}
