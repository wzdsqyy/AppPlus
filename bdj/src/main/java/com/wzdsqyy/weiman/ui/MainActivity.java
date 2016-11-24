package com.wzdsqyy.weiman.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.wzdsqyy.bdj.R;
import com.wzdsqyy.utils.helper.ImmerseHelper;
import com.wzdsqyy.weiman.ui.comics.fragment.ComicsFragment;

public class MainActivity extends AppCompatActivity{
    ImmerseHelper helper=new ImmerseHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,ComicsFragment.newInstance()).commitNow();
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    public void button(View view){
        if(view.isSelected()){
            helper.intoImmerse(getWindow());
        }else {
            helper.exitImmerse(getWindow());
        }
        view.setSelected(!view.isSelected());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
