package com.wzdsqyy.applibDemo.MutiItemList;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzdsqyy.mutiitem.MutiItemSuport;
import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.SpanSize;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.stickyheader.StickyLayout;

import java.util.ArrayList;
import java.util.Random;

public class MutiTypeActivity extends AppCompatActivity implements MutiItemBinderFactory, SpanSize {
    MutiItemAdapter adapter = new MutiItemAdapter(this);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muti_type);
        recyclerView = (RecyclerView) findViewById(R.id.list_item);
        adapter.diffSpanSizItem(recyclerView, this);
        adapter.register(Teacher.class, R.layout.item_teacher);
        adapter.register(StudentModel.class, R.layout.item_student);
        adapter.register(StickyModel.class, R.layout.item_sticky);
        StickyLayout.newInstance(this, R.layout.item_sticky)
                .setTarget(recyclerView);
    }

    public void randomData(View view) {
        Random random = new Random();
        int count = random.nextInt(20) + 200;
        ArrayList<MutiItemSuport> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                datas.add(new StickyModel("StickyModel_"));
            } else if (i % 5 == 2) {
                datas.add(new StudentModel());
            } else {
                datas.add(new Teacher());
            }
        }
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MutiItemBinder getMutiItemHolder(@LayoutRes int viewtype) {
        switch (viewtype) {
            case R.layout.item_teacher:
                return new TeacherView();
            case R.layout.item_student:
                return new StudentView();
            case R.layout.item_sticky:
                return new StickyView();
            default:
                return new StudentView();
        }
    }

    @Override
    public int getSpanCount() {
        return 1;
    }

    @Override
    public int getSpanSize(@LayoutRes int type) {
        return getSpanCount();
    }
}
