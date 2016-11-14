package com.wzdsqyy.weiman.ui.comics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.weiman.bean.Comics;
import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.ui.comics.adapter.ImageViewAdapter;
import com.wzdsqyy.weiman.ui.comics.presenter.CallBack;
import com.wzdsqyy.weiman.ui.comics.presenter.LoadPresenter;
import com.wzdsqyy.weiman.ui.common.LoadStatusHelper;

public class ComicsDetailActivity extends AppCompatActivity implements CallBack<Comics>,LoadStatusHelper.OnRetryButtonListener{
    private static final String TAG = "ComicsDetailActivity";
    private LoadPresenter<Comics> presenter;
    private ImageViewAdapter adapter;
    private RecyclerView mRvlist;
    private LoadStatusHelper helper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_detail);
        mRvlist= (RecyclerView) findViewById(R.id.comics_list);
        helper=new LoadStatusHelper(this,this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("Comics_Title");
        String id=getIntent().getStringExtra("Comics_Id");
        presenter=new LoadPresenter(this);
        adapter=new ImageViewAdapter(true);
        adapter.setViewLayoutManager(mRvlist);
        mRvlist.setHasFixedSize(false);
        Repository<Result<Comics>> comics = ComicsModel.getComics(id, presenter);
        presenter.setCompile(comics);
        presenter.startLoading();
        toolbar.setTitle(title);
    }

    public static void start(Context context,String id,String title) {
        Intent starter = new Intent(context, ComicsDetailActivity.class);
        starter.putExtra("Comics_Title",title);
        starter.putExtra("Comics_Id",id);
        context.startActivity(starter);
    }

    @Override
    public void onLoadError(Throwable ex) {
       helper.setStatus(LoadStatusHelper.ERROR);
    }

    @Override
    public void onSuccess(Comics value) {
        adapter.setData(value.imgList);
        helper.setStatus(LoadStatusHelper.SUCCESS);
        toolbar.setTitle(value.title);
    }

    @Override
    public void onRetryButtonClick(View v) {
        if (presenter != null) {
            presenter.startLoading();
            helper.setStatus(LoadStatusHelper.LOADING);
        }
    }
}
