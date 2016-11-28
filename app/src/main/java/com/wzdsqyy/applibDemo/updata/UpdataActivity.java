package com.wzdsqyy.applibDemo.updata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wzdsqyy.applibDemo.R;
import com.wzdsqyy.utils.okhttp.progress.ProgressListener;
import com.wzdsqyy.utils.updata.UpdateAgent;
import com.wzdsqyy.utils.updata.UpdateInfo;
import com.wzdsqyy.utils.updata.UpdateManager;
import com.wzdsqyy.utils.updata.UpdateUtil;

public class UpdataActivity extends AppCompatActivity implements ProgressListener,View.OnClickListener{
    @Override
    public boolean onProgress(long progress, long total, boolean done) {
        return false;
    }


    String mCheckUrl = "http://client.waimai.baidu.com/message/updatetag";

    String mUpdateUrl = "http://mobile.ac.qq.com/qqcomic_android.apk";


//    private static final String TAG = "UpdataActivity";
////    private UpDateManager manager;
//    private OkHttpClient client=new OkHttpClient();
//    private ProgressBar progressBar;
//    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        UpdateManager.setDebuggable(true);
        UpdateManager.setUrl(mCheckUrl, "yyb");
        UpdateManager.check(this);
        check(false, true, false, false, true, 998);
        findViewById(R.id.check_update).setOnClickListener(this);
        findViewById(R.id.check_update_cant_ignore).setOnClickListener(this);
        findViewById(R.id.check_update_force).setOnClickListener(this);
        findViewById(R.id.check_update_no_newer).setOnClickListener(this);
        findViewById(R.id.check_update_silent).setOnClickListener(this);
        findViewById(R.id.clean).setOnClickListener(this);
    }

    void check(boolean isManual, final boolean hasUpdate, final boolean isForce, final boolean isSilent, final boolean isIgnorable, final int notifyId) {
        UpdateManager.create(this).setUrl(mCheckUrl).setManual(isManual).setNotifyId(notifyId).setParser(new UpdateAgent.InfoParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = hasUpdate;
                info.updateContent = "• 支持文字、贴纸、背景音乐，尽情展现欢乐气氛；\n• 两人视频通话支持实时滤镜，丰富滤镜，多彩心情；\n• 图片编辑新增艺术滤镜，一键打造文艺画风；\n• 资料卡新增点赞排行榜，看好友里谁是魅力之王。";
                info.versionCode = 587;
                info.versionName = "v5.8.7";
                info.url = mUpdateUrl;
                info.md5 = "56cf48f10e4cf6043fbf53bbbc4009e3";
                info.size = 10149314;
                info.isForce = isForce;
                info.isIgnorable = isIgnorable;
                info.isSilent = isSilent;
                return info;
            }
        }).check();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_update:
                check(true, true, false, false, true, 998);
                break;
            case R.id.check_update_cant_ignore:
                check(true, true, false, false, false, 998);
                break;
            case R.id.check_update_force:
                check(true, true, true, false, true, 998);
                break;
            case R.id.check_update_no_newer:
                check(true, false, false, false, true, 998);
                break;
            case R.id.check_update_silent:
                check(true, true, false, true, true, 998);
                break;
            case R.id.clean:
                UpdateUtil.clean(this);
                Toast.makeText(this, "cleared", Toast.LENGTH_LONG).show();
                break;
        }
    }
//
//    public void updata(View view){
//        progressBar.setProgress(0);
//        manager.startForce("http://www.apk3.com/uploads/soft/guiguangbao/sjzs360.apk","sjzs360.apk");
//    }
//
//    @Override
//    public void showLoadingDialog(boolean canCancel) {
//        if(dialog==null){
//            dialog=new ProgressDialog(this,R.style.LoadDialog);
//            dialog.setTitle("正在下载");
//            dialog.setMax(100);
//            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            dialog.setCancelable(canCancel);
//            dialog.setCanceledOnTouchOutside(false);
//        }
//        dialog.setProgress(0);
//        dialog.show();
//    }
//
//    @Override
//    public void closeLoadingDialog(boolean fail) {
//        if(dialog!=null){
//            dialog.dismiss();
//        }
//    }
//
//    @Override
//    public Context getContext() {
//        return this;
//    }
//
//
//
//    public void stop(View view){
//        manager.cancel();
//    }
//
//
//
//    @Override
//    public boolean onProgress(long progress, long total, boolean done) {
//        Log.d(TAG, "onProgress() called with: progress = [" + progress + "], total = [" + total + "], done = [" + done + "]");
//        progressBar.setProgress((int) (progress*100/total));
//        dialog.setProgress((int) (progress*100/total));
//        return false;
//    }
}
