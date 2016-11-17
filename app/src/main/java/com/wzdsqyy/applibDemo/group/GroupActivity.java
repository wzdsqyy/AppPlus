package com.wzdsqyy.applibDemo.group;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements MutiItemBinderFactory {
    private RecyclerView recyclerView;
    private SectionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        recyclerView= (RecyclerView) findViewById(R.id.list_item);
        adapter=new SectionAdapter(this);
        adapter.setViewLayoutManager(recyclerView);
        adapter.register(SessionItem.class,R.layout.item_session);
        adapter.register(Item.class,R.layout.item_session_item);
        adapter.setData(initData());
        adapter.setSessionType(R.layout.item_session);
    }

    public void addBeans(View view){
        List data = initData(adapter.getItemCount());
        adapter.addMoreData(data);
    }
    private List initData(int start) {
        ArrayList list=new ArrayList();
        for (int i=start;i<start+100;i++){
            if(i%5==0){
                list.add(new SessionItem(i/5));
            }else {
                list.add(new Item(i/5));
            }
        }
        return list;
    }
    private List initData() {
        return initData(0);
    }

    @NonNull
    @Override
    public MutiItemBinder getMutiItemBinder(@LayoutRes int layoutRes, @NonNull ViewGroup parent) {
        switch (layoutRes){
            case R.layout.item_session:
                return new SessionItemBinder(adapter);
            default:
                return new ItemBinder(adapter);
        }
    }
}
