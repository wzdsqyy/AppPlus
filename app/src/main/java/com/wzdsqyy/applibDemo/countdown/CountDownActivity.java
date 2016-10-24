package com.wzdsqyy.applibDemo.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.utils.countdown.CountDownHelper;
import com.wzdsqyy.utils.countdown.CountDownListener;
import com.wzdsqyy.utils.countdown.TimerSupport;

import io.reactivex.plugins.RxJavaPlugins;

public class CountDownActivity extends AppCompatActivity implements CountDownListener, TimerSupport {
    private TextView text;
    private ListView listView;
    private long endTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        text= (TextView) findViewById(R.id.text);
        listView= (ListView) findViewById(R.id.list_item);
        AppAdapter adapter = new AppAdapter();
        listView.setAdapter(adapter);
        listView.setOnScrollListener(adapter);
        endTime=System.currentTimeMillis()+60*1000;
        CountDownHelper helper=new CountDownHelper(1000);
        helper.addCountDownListener(this);
    }

    @Override
    public void onCountDownTick(long time, boolean isFinish) {
        if(isFinish){
            text.setText("完成");
        }else {
            long have = endTime - System.currentTimeMillis();
            text.setText(have/1000+"s");
        }














    }

    @Override
    public boolean isCancel() {
        return false;
    }

    @Override
    public TimerSupport getTimerSupport() {
        return this;
    }

    @Override
    public boolean isFinish() {
        return System.currentTimeMillis()-endTime<0;
    }

    @Override
    public long endTime() {
        return endTime;
    }
}
