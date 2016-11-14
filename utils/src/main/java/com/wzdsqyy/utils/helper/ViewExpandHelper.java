//package com.wzdsqyy.utils.helper;
//
//import android.animation.ObjectAnimator;
//import android.util.Property;
//import android.view.View;
//import android.widget.TextView;
//
///**
// * Created by Administrator on 2016/9/20.
// */
//
//public class ViewExpandHelper {
//    private boolean isMaxLines;
//    private int lines;
//    private ObjectAnimator max2MinLinesObjectAnimator;
//    private ObjectAnimator min2MaxLinesObjectAnimator;
//    private Builder builder;
//    private OnFinishListener onFinishListener;
//
//    private ViewExpandHelper(Builder builder) {
//        this.builder = builder;
//        isMaxLines = false;
//        max2MinLinesObjectAnimator = ObjectAnimator.ofInt(this, new LineProperty(Integer.class, "max2Min"), builder.max.getHeight(), builder.target.getHeight());
//        min2MaxLinesObjectAnimator = ObjectAnimator.ofInt(this, new LineProperty(Integer.class, "min2Max"), builder.target.getHeight(), builder.max.getHeight());
//        max2MinLinesObjectAnimator.setDuration(builder.max2MinDuration);
//        min2MaxLinesObjectAnimator.setDuration(builder.min2MaxDuration);
//    }
//
//    public void toggleTextLayout() {
//        if (isMaxLines) {
//            max2MinLinesObjectAnimator.start();
//        } else {
//            min2MaxLinesObjectAnimator.start();
//        }
//        isMaxLines = !isMaxLines;
//    }
//
//    private final class LineProperty extends Property<ViewExpandHelper, Integer> {
//        private String name;
//
//        public LineProperty(Class<Integer> type, String name) {
//            super(type, name);
//            this.name = name;
//        }
//
//        @Override
//        public Integer get(ViewExpandHelper object) {
//            return object.getLines();
//        }
//
//        @Override
//        public void set(ViewExpandHelper object, Integer value) {
//            object.setLines(value);
//            if (onFinishListener == null) {
//                return;
//            }
//            if ("min2Max".equals(name)) {
//                if ((value == builder.max.getHeight()) && isMaxLines) {
//                    onFinishListener.onLineFinish(builder.target, true);
//                }
//                return;
//            }
//            if ("max2Min".equals(name)) {
//                if ((value == builder.target.getHeight()) && (!isMaxLines)) {
//                    onFinishListener.onLineFinish(builder.target, true);
//                }
//                return;
//            }
//        }
//    }
//
//    private void setLines(int max) {
//        builder.max.setMinimumHeight(max);
//        this.lines = max;
//    }
//
//    private int getLines() {
//        return lines;
//    }
//
//    public ViewExpandHelper onLineFinishListener(OnFinishListener onFinishListener) {
//        this.onFinishListener = onFinishListener;
//        return this;
//    }
//
//    public interface OnFinishListener {
//        /**
//         * @param textView
//         * @param isMin2Max 是 由最小行数 到 最大行数
//         */
//        void onLineFinish(View textView, boolean isMin2Max);
//    }
//
//    public static class Builder {
//        private long min2MaxDuration;
//        private long max2MinDuration;
//        private View target;
//        private View max;
//
//        public Builder min2MaxDuration(long min2MaxDuration) {
//            this.min2MaxDuration = min2MaxDuration;
//            return this;
//        }
//
//        public Builder max2MinDuration(long max2MinDuration) {
//            this.max2MinDuration = max2MinDuration;
//            return this;
//        }
//
//        public Builder minLines(View minLines) {
//            this.target = minLines;
//            return this;
//        }
//
//        public Builder maxLines(View maxLines) {
//            this.max = maxLines;
//            return this;
//        }
//
//        public ViewExpandHelper build() {
//            if (max == null || target == null) {
//                throw new RuntimeException("max==null||target==null");
//            }
//            if (max2MinDuration < 20) {
//                max2MinDuration = 300;
//            }
//            if (min2MaxDuration < 20) {
//                min2MaxDuration = 300;
//            }
//            return new ViewExpandHelper(this);
//        }
//    }
//}