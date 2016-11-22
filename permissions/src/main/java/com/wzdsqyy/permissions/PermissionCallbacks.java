package com.wzdsqyy.permissions;

import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {

    void onPermissionsGranted(int requestCode, List<String> perms);

    void onPermissionsDenied(int requestCode, List<String> perms);

}
