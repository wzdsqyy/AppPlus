package com.wzdsqyy.sharelite;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/11/23.
 */

public class Fragment extends android.support.v4.app.Fragment{
    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().commitNow();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
