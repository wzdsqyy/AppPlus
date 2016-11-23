package com.wzdsqyy.utils;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2016/11/23.
 */

public class LayoutInflaterWapper implements LayoutInflaterFactory {
    private LayoutInflater inflater;
    private AppCompatDelegate delegate;
    private LayoutInflaterFactory factory;

    public LayoutInflaterWapper setFactory(LayoutInflaterFactory factory) {
        this.factory = factory;
        return this;
    }

    public LayoutInflaterWapper(Context context, LayoutInflaterFactory factory) {
        this.inflater = LayoutInflater.from(context);
        this.factory = factory;
        if (context instanceof AppCompatActivity) {
            delegate = ((AppCompatActivity) context).getDelegate();
        }
        LayoutInflaterCompat.setFactory(inflater, this);
    }

    public static LayoutInflater from(Context context) {
        return from(context, null);
    }

    public static LayoutInflater from(Context context, LayoutInflaterFactory factory) {
        return new LayoutInflaterWapper(context, factory).inflater;
    }

    @Override
    public View onCreateView(View view, String s, Context context, AttributeSet attributeSet) {
        if (factory != null) {
            return factory.onCreateView(view, s, context, attributeSet);
        }
        if (delegate != null) {
            return delegate.createView(view, s, context, attributeSet);
        }
        return null;
    }
}
