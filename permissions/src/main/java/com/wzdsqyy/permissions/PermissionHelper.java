package com.wzdsqyy.permissions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by Administrator on 2016/11/11.
 */

public class PermissionHelper {
    //当用户勾选`不再询问`时, 进入设置界面
    public static void ent(Activity activity,int requestCode){
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        activity.startActivityForResult(intent, requestCode);
    }
}
