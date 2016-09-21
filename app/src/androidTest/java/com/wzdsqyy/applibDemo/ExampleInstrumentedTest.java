package com.wzdsqyy.applibDemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.wzdsqyy.applib.dialog.LoadingDialogController;
import com.wzdsqyy.applib.updata.UpDateManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wzdsqyy.applib", appContext.getPackageName());
    }

    public void testUpdata(){
        OkHttpClient okHttpClient=new OkHttpClient();
        LoadingDialogController controller=new LoadingDialogController() {
            @Override
            public void showLoadingDialog(boolean canCancel) {
                Log.d(TAG, "showLoadingDialog: ");
            }

            @Override
            public void closeLoadingDialog(boolean fail) {
                Log.d(TAG, "closeLoadingDialog: '");
            }

            @Override
            public Context getContext() {
                Log.d(TAG, "getContext: ");
                return getContext();
            }
        };
        UpDateManager manager=new UpDateManager(controller,okHttpClient);
        manager.startForce("http://a5.pc6.com/pc6_soure/2016-5/com.sqq.helper_12.apk","app.apk");
    }
}
