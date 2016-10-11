package com.wzdsqyy.applibDemo.updata;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.wzdsqyy.applib.ui.dialog.LoadingDialogController;
import com.wzdsqyy.applib.utils.okhttp.progress.ProgressListener;
import com.wzdsqyy.applib.utils.updata.UpDateManager;
import com.wzdsqyy.applibDemo.R;

import okhttp3.OkHttpClient;

public class UpdataActivity extends AppCompatActivity implements LoadingDialogController, ProgressListener {
    private static final String TAG = "UpdataActivity";
    private UpDateManager manager;
    private OkHttpClient client=new OkHttpClient();
    private ProgressBar progressBar;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        progressBar=(ProgressBar) findViewById(R.id.ProgressBar);
        manager=new UpDateManager(this,client);
        manager.setProgressListener(this);
    }

    public void updata(View view){
        progressBar.setProgress(0);
        manager.startForce("http://www.apk3.com/uploads/soft/guiguangbao/sjzs360.apk","sjzs360.apk");
    }

    @Override
    public void showLoadingDialog(boolean canCancel) {
        if(dialog==null){
            dialog=new ProgressDialog(this,R.style.LoadDialog);
            dialog.setTitle("正在下载");
            dialog.setMax(100);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(canCancel);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.setProgress(0);
        dialog.show();
    }

    @Override
    public void closeLoadingDialog(boolean fail) {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }



    public void stop(View view){
        manager.cancel();
    }



    @Override
    public boolean onProgress(long progress, long total, boolean done) {
        Log.d(TAG, "onProgress() called with: progress = [" + progress + "], total = [" + total + "], done = [" + done + "]");
        progressBar.setProgress((int) (progress*100/total));
        dialog.setProgress((int) (progress*100/total));
        return false;
    }
}
