package com.wzdsqyy.applibDemo;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/21.
 */

public class BigBandListener implements ClipboardManager.OnPrimaryClipChangedListener {
    private Context context;
    private static final String TAG = "BigBandListener";

    public BigBandListener(Context context) {
        this.context = context;
    }

    public void start(Context context) {
        android.content.ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.addPrimaryClipChangedListener(this);
    }

    @Override
    public void onPrimaryClipChanged() {
        android.content.ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        Log.d(TAG, "onPrimaryClipChanged: " + manager.getPrimaryClip().getItemCount());
        Log.d(TAG, "onPrimaryClipChanged: " + manager.getText());
    }
}
