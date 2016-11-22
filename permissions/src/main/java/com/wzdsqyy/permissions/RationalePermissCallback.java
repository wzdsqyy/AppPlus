package com.wzdsqyy.permissions;

/**
 * Created by Administrator on 2016/11/22.
 */

public interface RationalePermissCallback extends PermissionCallbacks {
    public void showRationaleDialog(int requestCode, String[] perms);
}
