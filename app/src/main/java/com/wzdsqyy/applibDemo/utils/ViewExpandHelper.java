package com.wzdsqyy.applibDemo.utils;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.util.Property;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/9/20.
 */

public class ViewExpandHelper {
    private boolean expand;
    private int lines;
    private ObjectAnimator max2MinLinesObjectAnimator;
    private ObjectAnimator min2MaxLinesObjectAnimator;
    private Builder builder;
    private OnFinishListener onFinishListener;

    private ViewExpandHelper(Builder builder) {
        this.builder = builder;
        expand = false;
        max2MinLinesObjectAnimator = ObjectAnimator.ofInt(this, new LineProperty(Integer.class, "max2Min"), builder.target.getHeight(), builder.expand.getHeight());
        min2MaxLinesObjectAnimator = ObjectAnimator.ofInt(this, new LineProperty(Integer.class, "min2Max"), builder.expand.getHeight(), builder.target.getHeight());
        max2MinLinesObjectAnimator.setDuration(builder.max2MinDuration);
        min2MaxLinesObjectAnimator.setDuration(builder.min2MaxDuration);
    }

    public void toggleTextLayout() {
        if (expand) {
            max2MinLinesObjectAnimator.start();
        } else {
            min2MaxLinesObjectAnimator.start();
        }
        expand = !expand;
    }

    private final class LineProperty extends Property<ViewExpandHelper, Integer> {
        private String name;

        public LineProperty(Class<Integer> type, String name) {
            super(type, name);
            this.name = name;
        }

        @Override
        public Integer get(ViewExpandHelper object) {
            return object.getLines();
        }

        @Override
        public void set(ViewExpandHelper object, Integer value) {
            object.setLines(value);
            if (onFinishListener == null) {
                return;
            }
            if ("min2Max".equals(name)) {
                if ((value == builder.target.getHeight()) && expand) {
                    onFinishListener.onLineFinish(builder.target, true);
                }
                return;
            }
            if ("max2Min".equals(name)) {
                if ((value == builder.expand.getHeight()) && (!expand)) {
                    onFinishListener.onLineFinish(builder.target, true);
                }
                return;
            }
        }
    }

    private void setLines(int max) {
        this.lines = max;
        this.builder.target.getLayoutParams().height=max;
        this.builder.target.invalidate();
    }

    private int getLines() {
        return lines;
    }

    public ViewExpandHelper onLineFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
        return this;
    }

    public interface OnFinishListener {
        /**
         * @param textView
         * @param isMin2Max 是 由最小行数 到 最大行数
         */
        void onLineFinish(View textView, boolean isMin2Max);
    }

    public static class Builder {
        private LinearLayout target;
        private OnFinishListener listener;
        private View expand;
        public long max2MinDuration=300;
        public long min2MaxDuration=300;

        public Builder(@NonNull LinearLayout target) {
            this.target = target;
        }

        public Builder setMax2MinDuration(long max2MinDuration) {
            this.max2MinDuration = max2MinDuration;
            return this;
        }

        public Builder setMin2MaxDuration(long min2MaxDuration) {
            this.min2MaxDuration = min2MaxDuration;
            return this;
        }

        public Builder setExpand(View expand) {
            this.expand = expand;
            return this;
        }

        public Builder setListener(OnFinishListener listener) {
            this.listener = listener;
            return this;
        }

        public ViewExpandHelper build() {
            return new ViewExpandHelper(this);
        }
    }
}