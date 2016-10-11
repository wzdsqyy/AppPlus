package com.wzdsqyy.applibDemo.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.wzdsqyy.applib.countdown.CountDownListener;
import com.wzdsqyy.applib.countdown.TimerSupport;
import com.wzdsqyy.applibDemo.R;

public class CountDownActivity extends AppCompatActivity implements TimerSupport,CountDownListener {
    private TextView text;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        text= (TextView) findViewById(R.id.text);
        listView= (ListView) findViewById(R.id.list_item);
        AppAdapter adapter = new AppAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void onCountDownTick(int totalTime, boolean isFinish) {
        if(!isFinish){
            text.setText(totalTime+"s");
        }else{
            text.setText("结书");
        }
    }

    @Override
    public boolean isCancel() {
        return false;
    }

    @Override
    public long countDownInterval() {
        return 1000;
    }

    @Override
    public long getEndTime() {
        return System.currentTimeMillis()+15*1000;
    }
}
