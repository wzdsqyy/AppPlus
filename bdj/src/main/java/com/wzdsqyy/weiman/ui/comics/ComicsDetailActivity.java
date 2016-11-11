package com.wzdsqyy.weiman.ui.comics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.agera.Receiver;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.wzdsqyy.bdj.R;
import com.wzdsqyy.weiman.bean.Comics;
import com.wzdsqyy.weiman.data.model.ComicsModel;
import com.wzdsqyy.weiman.ui.comics.adapter.ImageViewAdapter;
import com.wzdsqyy.weiman.ui.comics.viewmodel.LoadPresenter;

public class ComicsDetailActivity extends AppCompatActivity implements Receiver<Comics> {
    private static final int SUCCESS = 1, LOADING = 2, EMPTY = 3, ERROR = 4;
    private int mStatus = LOADING;
    private static final String TAG = "ComicsDetailActivity";
    private LoadPresenter<Comics> presenter;
    private ImageViewAdapter adapter;
    private RecyclerView mRvlist;
    private View mLoading;
    private View mEmpty;
    private View mError;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_detail);
        mRvlist= (RecyclerView) findViewById(R.id.comics_list);
        mLoading = findViewById(R.id.common_loading);
        mEmpty = findViewById(R.id.common_empty);
        mError = findViewById(R.id.common_failtrue);
        mError.findViewById(R.id.common_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    presenter.startLoading();
                    showLoading();
                }
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra("Comics_Title");
        String id=getIntent().getStringExtra("Comics_Id");
        presenter=new LoadPresenter(null,this);
        adapter=new ImageViewAdapter(true);
        adapter.setViewLayoutManager(mRvlist);
        mRvlist.setHasFixedSize(false);
        Repository<Result<Comics>> comics = ComicsModel.getComics(id, presenter);
        presenter.setCompile(comics);
        presenter.startLoading();
    }

    private void showLoading() {
        mStatus = LOADING;
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.bringToFront();
    }

    private void showError() {
        mStatus = ERROR;
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    private void showEmpety() {
        mStatus = EMPTY;
        mEmpty.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void showSucess() {
        mStatus = SUCCESS;
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    public static void start(Context context,String id,String title) {
        Intent starter = new Intent(context, ComicsDetailActivity.class);
        starter.putExtra("Comics_Title",title);
        starter.putExtra("Comics_Id",id);
        context.startActivity(starter);
    }

    @Override
    public void accept(@NonNull Comics value) {
        adapter.setData(value.imgList);
        showSucess();
        toolbar.setTitle(value.title);
    }
}
