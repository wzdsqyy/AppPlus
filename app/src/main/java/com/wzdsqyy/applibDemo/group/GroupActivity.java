package com.wzdsqyy.applibDemo.group;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.Node;
import com.wzdsqyy.mutiitem.internal.NodeAdapter;
import com.wzdsqyy.mutiitem.internal.NodeList;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity implements MutiItemBinderFactory {
    private RecyclerView recyclerView;
    private NodeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        recyclerView= (RecyclerView) findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        adapter=new NodeAdapter(this);
        adapter.setViewLayoutManager(recyclerView);
        adapter.register(SessionItem.class,R.layout.item_session);
        adapter.register(Item.class,R.layout.item_session_item);
        adapter.setData(initData());
    }

    public void addBeans(View view){
        NodeList data = initData(adapter.getItemCount());
        adapter.addMoreData(data);
    }
    private NodeList initData(int start) {
        NodeList list=NodeList.getNodeList(false,new ArrayList());
        for (int i=start;i<start+20;i++){
            SessionItem item = new SessionItem(i);
            ArrayList<Node> subs=new ArrayList(5);
            for (int j = 0; j < 5; j++) {
                Item node = new Item(j);
                node.setSection(i);
                subs.add(node);
            }
            item.setChilds(subs);
            list.add(item);
        }
        return list;
    }
    private NodeList initData() {
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
