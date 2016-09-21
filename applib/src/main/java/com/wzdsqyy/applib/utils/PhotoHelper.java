package com.wzdsqyy.applib.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Qiuyy on 2016/7/23.
 */
public class PhotoHelper {
    private static final String TAG = "PhotoHelper";
    private final File path;
    public File savePath;
    private int mChoosePhoto = -1;
    private int mTakePhoto = -1;
    private Callback callback;

    public PhotoHelper(Callback callback) {
        path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        path.mkdirs();
        this.callback = callback;
    }

    public void startLoacalPhoto(Activity activity, int requestCode) {
        mChoosePhoto = requestCode;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); /* 开启Pictures画面Type设定为image */
        intent.setType("image/*"); /* 使用Intent.ACTION_GET_CONTENT这个Action *//* 取得相片后返回本画面 */
        activity.startActivityForResult(intent, requestCode);
    }

    public void startLoacalPhoto(Fragment fragment, int requestCode) {
        mChoosePhoto = requestCode;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); /* 开启Pictures画面Type设定为image */
        intent.setType("image/*"); /* 使用Intent.ACTION_GET_CONTENT这个Action *//* 取得相片后返回本画面 */
        fragment.startActivityForResult(intent, requestCode);
    }

    public void takePhoto(Activity activity, int requestCode) {
        File file = new File(path, "bqu_" + System.currentTimeMillis() + ".jpg");
        takePhoto(activity, file.getPath(), requestCode);
    }

    public void takePhoto(Fragment fragment, int requestCode) {
        File file = new File(path, "bqu_" + System.currentTimeMillis() + ".jpg");
        takePhoto(fragment, file.getPath(), requestCode);
    }

    public void takePhoto(Fragment fragment, String path, int requestCode) {
        mTakePhoto = requestCode;
        savePath = new File(path);
        if (!savePath.exists()) {
            try {
                savePath.createNewFile();
            } catch (IOException e) {
                savePath = null;
                callback.onPhotoResult(e.getMessage(), e, requestCode);
            }
        }
        if (savePath != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(savePath));
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public void takePhoto(Activity activity, String path, int requestCode) {
        mTakePhoto = requestCode;
        savePath = new File(path);
        if (!savePath.exists()) {
            try {
                savePath.createNewFile();
            } catch (IOException e) {
                savePath = null;
                callback.onPhotoResult(e.getMessage(), e, requestCode);
            }
        }
        if (savePath != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(savePath));
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == mTakePhoto) {
                callback.onPhotoResult(savePath.getPath(), null, requestCode);
                return true;
            } else if (requestCode == mChoosePhoto) {
                if (data.getData().getScheme().equals("file")) {
                    callback.onPhotoResult(data.getData().getPath(), null, requestCode);
                    return true;
                } else if (data.getData().getScheme().equals("content")) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cur = activity.getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                    int index;
                    if (cur != null && cur.moveToFirst()) {
                        index = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        cur.getExtras().toString();
                        if (index > -1) {
                            String path = cur.getString(index);
                            if (!TextUtils.isEmpty(path)) {
                                callback.onPhotoResult(path, null, requestCode);
                                return true;
                            }
                        }
                    }
                }
                callback.onPhotoResult(null, new FileNotFoundException(), requestCode);
            }
        }
        return false;
    }

    public interface Callback {
        /**
         * @param path 图片存储路径
         * @param e    成功时为null
         */
        void onPhotoResult(String path, Exception e, int requestCode);
    }
}
