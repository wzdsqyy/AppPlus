package com.wzdsqyy.applib.utils.updata;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.wzdsqyy.applib.ui.dialog.LoadingDialogController;
import com.wzdsqyy.applib.utils.okhttp.progress.ProgressHelper;
import com.wzdsqyy.applib.utils.okhttp.progress.ProgressListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/20.
 */

public class UpDateManager implements Callback, ProgressListener {
    LoadingDialogController controller;
    String mDownUrl;
    File mSave;
    Handler handler;
    ProgressListener mListener;
    OkHttpClient client;
    volatile boolean cancel = false;

    public UpDateManager(@NonNull LoadingDialogController controller, @NonNull OkHttpClient client) {
        this.controller = controller;
        this.client = client;
    }

    public UpDateManager setProgressListener(ProgressListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public void cancel() {
        cancel = true;
    }

    public UpDateManager startForce(String mDownUrl, String name) {
        this.mSave = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), name);
        this.mDownUrl = mDownUrl;
        controller.showLoadingDialog(false);
        Request request = new Request.Builder().get().url(mDownUrl).build();
        handler = new Handler(Looper.getMainLooper());
        ProgressHelper.download(client, request, this, this);
//        client.newCall(request).enqueue(this);
        return this;
    }

    public void setSavePath(File mSave) {
        this.mSave=mSave;
    }

    public UpDateManager start(String mDownUrl, String name) {
        this.mSave = new File(controller.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        this.mDownUrl = mDownUrl;
        controller.showLoadingDialog(true);
        Request request = new Request.Builder().get().url(mDownUrl).build();
        handler = new Handler(Looper.getMainLooper());
        ProgressHelper.download(client, request, this, this);
        return this;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        controller.closeLoadingDialog(true);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mSave.deleteOnExit();
        mSave.createNewFile();
        FileOutputStream outputStream=new FileOutputStream(mSave);
        InputStream stream = response.body().byteStream();
        byte[] bs = new byte[1024];
        int len;
        while ((len = stream.read(bs)) != -1) {
            outputStream.write(bs, 0, len);
        }
        if (!cancel) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    controller.closeLoadingDialog(false);
                    controller.getContext().startActivity(install(mSave));
                }
            });
        }
    }

    public Intent install(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    private void deleteFile() {
        if (mSave != null) {
            mSave.setWritable(true);
            mSave.deleteOnExit();
        }
    }

    @Override
    public boolean onProgress(final long progress, final long total, final boolean done) {
        if (cancel) {
            return false;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                cancel = mListener.onProgress(progress, total, done);
            }
        });
        return true;
    }
}
