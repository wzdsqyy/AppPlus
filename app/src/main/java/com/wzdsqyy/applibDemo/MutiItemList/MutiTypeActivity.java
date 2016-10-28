package com.wzdsqyy.applibDemo.MutiItemList;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.commonview.IndexBar;
import com.wzdsqyy.commonview.OnIndexTouchListener;
import com.wzdsqyy.mutiitem.MutiItemAdapter;
import com.wzdsqyy.mutiitem.MutiItemBinder;
import com.wzdsqyy.mutiitem.MutiItemBinderFactory;
import com.wzdsqyy.mutiitem.MutiItemSuport;
import com.wzdsqyy.mutiitem.SpanSize;
import com.wzdsqyy.stickyheader.StickyLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MutiTypeActivity extends AppCompatActivity implements MutiItemBinderFactory, SpanSize, OnIndexTouchListener, ClipboardManager.OnPrimaryClipChangedListener {
    MutiItemAdapter adapter = new MutiItemAdapter(this);
    RecyclerView recyclerView;
    IndexBar indexview;
    private static final String TAG = "MutiTypeActivity";
    private List<String> mlist;
    private Toast toast;
    private TextView index_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muti_type);
        indexview = (IndexBar) findViewById(R.id.indexview);
        recyclerView = (RecyclerView) findViewById(R.id.list_item);
        adapter.diffSpanSizItem(recyclerView, this);
        adapter.register(Teacher.class, R.layout.item_teacher);
        adapter.register(StudentModel.class, R.layout.item_student);
        adapter.register(StickyModel.class, R.layout.item_sticky);
        indexview.setIndexList(initIndexList());
        indexview.setOnIndexTouchListener(this);
        index_notice= (TextView) findViewById(R.id.index_notice);
        StickyLayout.newInstance(this, R.layout.item_sticky)
                .setTarget(recyclerView,(ViewGroup)recyclerView.getParent(),true);
    }

    private List<String> initIndexList() {
        String[] array = getResources().getStringArray(R.array.indexable_letter);
        mlist=Arrays.asList(array);
        return mlist;
    }

    public void randomData(View view) {
        Random random = new Random();
        int count = random.nextInt(20) + 200;
        ArrayList<MutiItemSuport> datas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 5 == 3) {
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

    @Override
    public void onIndexTouch(IndexBar view, boolean isTouch, int select) {
        index_notice.setVisibility(isTouch?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void onIndexScroll(IndexBar view, int index) {
        index_notice.setText(mlist.get(index));




    }

    @Override
    public void onPrimaryClipChanged() {
        android.content.ClipboardManager manager= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.getPrimaryClip().getItemCount();
    }
}
