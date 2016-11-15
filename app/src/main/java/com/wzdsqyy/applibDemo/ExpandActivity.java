package com.wzdsqyy.applibDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ExpandActivity extends AppCompatActivity {

    private static final String TAG = "ExpandActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
    }

    public void expand(View view){
        Log.d(TAG, "expand() called with: view = [" + view + "]");








    }
}
