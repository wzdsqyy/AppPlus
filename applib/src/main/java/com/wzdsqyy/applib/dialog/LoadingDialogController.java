package com.wzdsqyy.applib.dialog;

import android.content.Context;

/**
 * Created by Administrator on 2016/9/20.
 */

public interface LoadingDialogController {
    void showLoadingDialog(boolean canCancel);
    void closeLoadingDialog(boolean fail);
    Context getContext();
}
