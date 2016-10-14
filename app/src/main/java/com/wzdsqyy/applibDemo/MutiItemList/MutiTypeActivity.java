package com.wzdsqyy.applibDemo.MutiItemList;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzdsqyy.applib.ui.adapter.ItemTypeSuport;
import com.wzdsqyy.applib.ui.adapter.MutiAdapter;
import com.wzdsqyy.applib.ui.adapter.MutiItemBinder;
import com.wzdsqyy.applib.ui.adapter.SpanSize;
import com.wzdsqyy.applib.ui.adapter.ViewModelFactory;
import com.wzdsqyy.applibDemo.R;

import java.util.ArrayList;
import java.util.Random;

public class MutiTypeActivity extends AppCompatActivity implements ViewModelFactory,SpanSize{
    MutiAdapter adapter = new MutiAdapter(this);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muti_type);
        recyclerView = (RecyclerView) findViewById(R.id.list_item);
        adapter.diffSpanSizItem(recyclerView,this);
        adapter.register(Teacher.class,R.layout.item_teacher);
        adapter.register(StudentModel.class,R.layout.item_student);
    }

    public void randomData(View view) {
        Random random = new Random();
        int count = random.nextInt(20) + 30;
        ArrayList<ItemTypeSuport> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 3 == 0) {
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
        switch (viewtype){
            case R.layout.item_teacher:
                return new TeacherView();
            case R.layout.item_student:
                return new StudentView();
        }
        return null;
    }

    @Override
    public int getSpanCount() {
        return 3;
    }

    @Override
    public int getSpanSize(@LayoutRes int type) {
        switch (type){
            case R.layout.item_teacher:
                return 2;
            case R.layout.item_student:
                return 1;
        }
        return getSpanCount();
    }
}
