package com.wzdsqyy.applibDemo.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/10/11.
 */

public class LoginWapper extends Activity implements View.OnClickListener {
    private View.OnClickListener listener;
    private LoginControllor controllor;
    private Intent login;
    private Activity activity;
    private View eventView;
    private int loginRequestCode=1;
    @Override
    public void onClick(View v) {
        if(controllor.needLoginClick(v)){
            eventView=v;




            test(this);

            activity.startActivityForResult(login,loginRequestCode);
        }else {
            listener.onClick(eventView);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(loginRequestCode==requestCode){
            if(Activity.RESULT_OK==resultCode){
                listener.onClick(eventView);
            }
        }
    }

    public void test(Object o){




    }

    public void test(LoginWapper wapper){

    }

    public void test(Activity wapper){

    }


    public void test(View.OnClickListener listener){

    }

}
