package com.wzdsqyy.applibDemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BigBangService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BigBandListener listener=new BigBandListener(this);
        listener.start(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, BigBangService.class);
        context.startService(starter);
    }
}
