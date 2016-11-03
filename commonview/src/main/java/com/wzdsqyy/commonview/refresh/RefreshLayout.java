package com.wzdsqyy.commonview.refresh;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.OverScroller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by liaoinstan on 2016/3/11.
 */
public class RefreshLayout extends FrameLayout{
    @IntDef({IDLE, PULL_MOVE, PULL_UP_LOAD,PULL_DOWN_REFRESH,PULL_RELEASE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status{}
    private static final int IDLE = 0;
    private static final int PULL_MOVE = 1;
    private static final int PULL_UP_LOAD = 2;
    private static final int PULL_DOWN_REFRESH = 3;
    private static final int PULL_RELEASE = 4;
    @RefreshLayout.Status
    private int status=IDLE;


    private float dy;
    private float dx;
    private boolean isNeedMyMove;

    /**
     * 处理多点触控的情况，准确地计算Y坐标和移动距离dy
     * 同时兼容单点触控的情况
     */
    private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;
    public void dealMulTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                mLastX = x;
                mLastY = y;
                mActivePointerId = ev.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                dx = x - mLastX;
                dy = y - mLastY;
                mLastY = y;
                mLastX = x;
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId != mActivePointerId) {
                    mLastX = MotionEventCompat.getX(ev, pointerIndex);
                    mLastY = MotionEventCompat.getY(ev, pointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastX = MotionEventCompat.getX(ev, newPointerIndex);
                    mLastY = MotionEventCompat.getY(ev, newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                break;
            }
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        dealMulTouchEvent(event);
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                hasCallFull = false;
                hasCallRefresh = false;
                mfirstY = event.getY();
                boolean isTop = isChildScrollToTop();
                boolean isBottom = isChildScrollToBottomFull(isFullEnable);
                if (isTop||isBottom) isNeedMyMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                dsY += dy;
                isMoveNow = true;
                isNeedMyMove = isNeedMyMove();
                if(isNeedMyMove){
                    status=PULL_MOVE;
                    if(!isInControl){
                        //把内部控件的事件转发给本控件处理
                        isInControl = true;
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(event);
                        dispatchTouchEvent(event);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isMoveNow = false;
                lastMoveTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isNeedMyMove && enable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (contentView==null){
            return false;
        }
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                isFirst = true;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();//不需要处理
                }
                break;
            case MotionEvent.ACTION_MOVE:





















                if (isNeedMyMove){
                    needResetAnim = false;      //按下的时候关闭回弹
                    //执行位移操作
                    doMove();
                    //下拉的时候显示header并隐藏footer，上拉的时候相反
                    if (isTop()){
                        if (header!=null && header.getVisibility() != View.VISIBLE) header.setVisibility(View.VISIBLE);
                        if (footer!=null && footer.getVisibility() != View.INVISIBLE) footer.setVisibility(View.INVISIBLE);
                    }else if(isBottom()){
                        if (header!=null && header.getVisibility() != View.INVISIBLE) header.setVisibility(View.INVISIBLE);
                        if (footer!=null && footer.getVisibility() != View.VISIBLE) footer.setVisibility(View.VISIBLE);
                    }
                    //回调onDropAnim接口
                    callOnDropAnim();
                    //回调callOnPreDrag接口
                    callOnPreDrag();
                    //回调onLimitDes接口
                    callOnLimitDes();
                    isFirst = false;
                }
                else {
                    //手指在产生移动的时候（dy!=0）才重置位置
                    if (dy!=0 && isFlow()) {
                        resetPosition();
                        //把滚动事件交给内部控件处理
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                last_top = 0;
                needResetAnim = true;      //松开的时候打开回弹
                isFirst = true;
                _firstDrag = true;
                restSmartPosition();
                dsY = 0;
                dy = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }else {
            if(status==PULL_DOWN_REFRESH){
                
            }else if (status==PULL_RELEASE){

            }
        }
    }




























































    private OverScroller mScroller;
    private OnFreshListener listener;         //监听回调
    private boolean isCallDown = false;     //用于判断是否在下拉时到达临界点
    private boolean isCallUp = false;       //用于判断是否在上拉时到达临界点
    private boolean isFirst = true;         //用于判断是否是拖动动作的第一次move
    private boolean needChange = false;     //是否需要改变样式
    private boolean needResetAnim = false;  //是否需要弹回的动画
    private boolean isFullEnable = false;   //是否超过一屏时才允许上拉，为false则不满一屏也可以上拉，注意样式为isOverlap时，无论如何也不允许在不满一屏时上拉
    private boolean isMoveNow = false;       //当前是否正在拖动
    private long lastMoveTime;
    private boolean enable = true;           //是否禁用（默认可用）
    private int headerResoureId;
    private int footerResoureId;
    private int MOVE_TIME = 400;
    private int MOVE_TIME_OVER = 200;

    //是否需要回调接口：TOP 只回调刷新、BOTTOM 只回调加载更多、BOTH 都需要、NONE 都不
    public enum Give {BOTH, TOP, BOTTOM,NONE};
    public enum Type {OVERLAP,FOLLOW};
    private Give give = Give.BOTH;
    private Type type = Type.OVERLAP;
    private Type _type;

    //移动参数：计算手指移动量的时候会用到这个值，值越大，移动量越小，若值为1则手指移动多少就滑动多少px
    private final double MOVE_PARA = 2;
    //最大拉动距离，拉动距离越靠近这个值拉动就越缓慢
    private int MAX_HEADER_PULL_HEIGHT = 600;
    private int MAX_FOOTER_PULL_HEIGHT = 600;
    //拉动多少距离被认定为刷新(加载)动作
    private int HEADER_LIMIT_HEIGHT;
    private int FOOTER_LIMIT_HEIGHT;
    private int HEADER_SPRING_HEIGHT;
    private int FOOTER_SPRING_HEIGHT;
    //储存上次的Y坐标
    private float mLastY;
    private float mLastX;
    //储存第一次的Y坐标
    private float mfirstY;
    //储存手指拉动的总距离
    private float dsY;
    //滑动事件目前是否在本控件的控制中
    private boolean isInControl = false;
    //存储拉动前的位置
    private Rect mRect = new Rect();

    //头尾内容布局
    private View header;
    private View footer;
    private View contentView;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount()>0){
            for (int i=0;i<getChildCount();i++){
                View child = getChildAt(i);
                measureChild(child,widthMeasureSpec,heightMeasureSpec);
            }
        }
        //如果是动态设置的头部，则使用动态设置的参数
        if (mHeader!=null){
            header= (View) mHeader;
            //设置下拉最大高度，只有在>0时才生效，否则使用默认值
            int xh = mHeader.getDragMaxHeight(header);
            if (xh>0) MAX_HEADER_PULL_HEIGHT = xh;
            //设置下拉临界高度，只有在>0时才生效，否则默认为header的高度
            int h = mHeader.getDragLimitHeight(header);
            HEADER_LIMIT_HEIGHT = h>0 ? h : header.getMeasuredHeight();
            //设置下拉弹动高度，只有在>0时才生效，否则默认和临界高度一致
            int sh = mHeader.getDragSpringHeight(header);
            HEADER_SPRING_HEIGHT = sh>0 ? sh : HEADER_LIMIT_HEIGHT;
        }else {
            //不是动态设置的头部，设置默认值
            if (header!=null) HEADER_LIMIT_HEIGHT = header.getMeasuredHeight();
            HEADER_SPRING_HEIGHT = HEADER_LIMIT_HEIGHT;
        }
        //设置尾部参数，和上面一样
        if (mFooter!=null){
            footer= (View) mFooter;
            int xh = mFooter.getDragMaxHeight(footer);
            if (xh>0) MAX_FOOTER_PULL_HEIGHT = xh;
            int h = mFooter.getDragLimitHeight(footer);
            FOOTER_LIMIT_HEIGHT = h>0 ? h : footer.getMeasuredHeight();
            int sh = mFooter.getDragSpringHeight(footer);
            FOOTER_SPRING_HEIGHT = sh>0 ? sh : FOOTER_LIMIT_HEIGHT;
        }else {
            if (footer!=null) FOOTER_LIMIT_HEIGHT = footer.getMeasuredHeight();
            FOOTER_SPRING_HEIGHT = FOOTER_LIMIT_HEIGHT;
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (contentView!=null) {
            if(type== Type.OVERLAP){
                if (header!=null) {
                    header.layout(0, 0, getWidth(), header.getMeasuredHeight());
                }
                if (footer!=null) {
                    footer.layout(0, getHeight() - footer.getMeasuredHeight(), getWidth(), getHeight());
                }
            }else if(type== Type.FOLLOW){
                if (header!=null) {
                    header.layout(0, -header.getMeasuredHeight(), getWidth(), 0);
                }
                if (footer!=null) {
                    footer.layout(0, getHeight(), getWidth(), getHeight() + footer.getMeasuredHeight());
                }
            }
            contentView.layout(0, 0, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
        }
    }






    private int last_top;
    private void doMove(){
        if (type== Type.OVERLAP){
            //记录移动前的位置
            if (mRect.isEmpty()) {
                mRect.set(contentView.getLeft(), contentView.getTop(),contentView.getRight(), contentView.getBottom());
            }
            //根据下拉高度计算位移距离，（越拉越慢）
            int movedy;
            if (dy>0) {
                movedy = (int) ((float) ((MAX_HEADER_PULL_HEIGHT - contentView.getTop()) / (float) MAX_HEADER_PULL_HEIGHT) * dy / MOVE_PARA);
            }else {
                movedy = (int) ((float) ((MAX_FOOTER_PULL_HEIGHT - (getHeight()-contentView.getBottom())) / (float) MAX_FOOTER_PULL_HEIGHT) * dy / MOVE_PARA);
            }
            int top = contentView.getTop() + movedy;
            contentView.layout(contentView.getLeft(), top, contentView.getRight(), top + contentView.getMeasuredHeight());
        }else if(type== Type.FOLLOW){
            //根据下拉高度计算位移距离，（越拉越慢）
            int movedx;
            if (dy>0) {
                movedx = (int) ((float) ((MAX_HEADER_PULL_HEIGHT + getScrollY()) / (float) MAX_HEADER_PULL_HEIGHT) * dy / MOVE_PARA);
            }else {
                movedx = (int) ((float) ((MAX_FOOTER_PULL_HEIGHT - getScrollY()) / (float) MAX_FOOTER_PULL_HEIGHT) * dy / MOVE_PARA);
            }
            scrollBy(0, (int)(-movedx));
        }
    }

    private void callOnDropAnim(){
        if (type== Type.OVERLAP){
            if (contentView.getTop()>0)
                if(mHeader!=null) mHeader.onMove(header,contentView.getTop());
            if (contentView.getTop()<0)
                if(mFooter!=null) mFooter.onMove(footer,contentView.getTop());
        }else if(type== Type.FOLLOW){
            if (getScrollY()<0)
                if(mHeader!=null) mHeader.onMove(header,-getScrollY());
            if (getScrollY()>0)
                if(mFooter!=null) mFooter.onMove(footer,-getScrollY());
        }
    }

    private boolean _firstDrag = true;
    private void callOnPreDrag() {
        if (_firstDrag){
            if (isTop()){
                if (mHeader!=null) mHeader.onPreDrag(header);
                _firstDrag = false;
            }else if(isBottom()){
                if (mFooter!=null) mFooter.onPreDrag(footer);
                _firstDrag = false;
            }
        }
    }

    private void callOnLimitDes(){
        boolean topORbottom = false;
        if (type== Type.OVERLAP){
            topORbottom = contentView.getTop()>=0 && isChildScrollToTop();
        }else if(type== Type.FOLLOW){
            topORbottom = getScrollY()<=0 && isChildScrollToTop();
        }
        if (isFirst){
            if (topORbottom){
                isCallUp = true;
                isCallDown = false;
            }else {
                isCallUp = false;
                isCallDown = true;
            }
        }
        if (dy==0) return;
        boolean upORdown = dy<0;
        if (topORbottom){
            if (!upORdown){
                if ((isTopOverFarm()) && !isCallDown){
                    isCallDown = true;
                    if(mHeader!=null) mHeader.onLimitDes(header,upORdown);
                    isCallUp = false;
                }
            }else {
                if (!isTopOverFarm() && !isCallUp){
                    isCallUp = true;
                    if(mHeader!=null) mHeader.onLimitDes(header,upORdown);
                    isCallDown = false;
                }
            }
        }else {
            if (upORdown){
                if (isBottomOverFarm() && !isCallUp){
                    isCallUp = true;
                    if(mFooter!=null) mFooter.onLimitDes(footer,upORdown);
                    isCallDown = false;
                }
            }else {
                if (!isBottomOverFarm() && !isCallDown){
                    isCallDown = true;
                    if(mFooter!=null) mFooter.onLimitDes(footer,upORdown);
                    isCallUp = false;
                }
            }
        }
    }

    private int callFreshORload = 0;
    private boolean isFullAnim;
    private boolean hasCallFull = false;
    private boolean hasCallRefresh = false;

    /**
     * 判断是否需要由该控件来控制滑动事件
     */
    private boolean isNeedMyMove() {
        if (contentView==null){
            return false;
        }
        if (Math.abs(dy)<Math.abs(dx)){
            return false;
        }
        boolean isTop = isChildScrollToTop();
        boolean isBottom = isChildScrollToBottomFull(isFullEnable);     //false不满一屏也算在底部，true不满一屏不算在底部
        if (type== Type.OVERLAP){
            if (header!=null) {
                if (isTop && dy > 0 || contentView.getTop() > 0 + 20) {
                    return true;
                }
            }
            if (footer!=null) {
                if (isBottom && dy < 0 || contentView.getBottom() < mRect.bottom - 20) {
                    return true;
                }
            }
        }else if(type== Type.FOLLOW){
            if (header!=null) {
                //其中的20是一个防止触摸误差的偏移量
                if (isTop && dy > 0 || getScrollY() < 0 - 20) {
                    return true;
                }
            }
            if (footer!=null) {
                if (isBottom && dy < 0 || getScrollY() > 0 + 20) {
                    return true;
                }
            }
        }
        return false;
    }

    private void callOnAfterFullAnim(){
        if (callFreshORload!=0) {
            callOnFinishAnim();
        }
        if (needChangeHeader){
            needChangeHeader = false;
//            setHeaderIn(_mHeader);
        }
        if (needChangeFooter){
            needChangeFooter = false;
//            setFooterIn(_footerHander);
        }
        //动画完成后检查是否需要切换type，是则切换
        if (needChange){
            changeType(_type);
        }
    }
    private void callOnAfterRefreshAnim(){
        if (type == Type.FOLLOW) {
            if (isTop()) {
                listener.onRefresh();
            } else if (isBottom()) {
                listener.onLoadmore();
            }
        }else if(type == Type.OVERLAP){
            if (!isMoveNow) {
                long nowtime = System.currentTimeMillis();
                if (nowtime-lastMoveTime>=MOVE_TIME_OVER){
                    if (callFreshORload == 1)
                        listener.onRefresh();
                    if (callFreshORload == 2)
                        listener.onLoadmore();
                }
            }
        }
    }

    /**
     * 重置控件位置到初始状态
     */
    private void resetPosition() {
        isFullAnim = true;
        isInControl = false;    //重置位置的时候，滑动事件已经不在控件的控制中了
        if (type== Type.OVERLAP){
            if (mRect.bottom==0||mRect.right==0) return;
            //根据下拉高度计算弹回时间，时间最小100，最大400
            int time = 0;
            if (contentView.getHeight()>0) {
                time = Math.abs(400 * contentView.getTop() / contentView.getHeight());
            }
            if(time<100) time = 100;

            Animation animation = new TranslateAnimation(0, 0, contentView.getTop(),mRect.top);
            animation.setDuration(time);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    callOnAfterFullAnim();
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            contentView.startAnimation(animation);
            contentView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        }else if(type== Type.FOLLOW){
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY(),MOVE_TIME);
            invalidate();
        }
        //mRect.setEmpty();
    }
    private void callOnFinishAnim(){
        if (callFreshORload!=0) {
            if (callFreshORload == 1) {
                if (mHeader != null) mHeader.onFinishAnim();
                if (give == Give.BOTTOM || give == Give.NONE){
                    listener.onRefresh();
                }
            } else if (callFreshORload == 2) {
                if (mFooter != null) mFooter.onFinishAnim();
                if (give == Give.TOP || give == Give.NONE){
                    listener.onLoadmore();
                }
            }
            callFreshORload=0;
        }
    }

    /**
     * 重置控件位置到刷新状态（或加载状态）
     */
    private void resetRefreshPosition() {
        isFullAnim = false;
        isInControl = false;    //重置位置的时候，滑动事件已经不在控件的控制中了
        if (type== Type.OVERLAP){
            if (mRect.bottom==0||mRect.right==0) return;
            if (contentView.getTop()>mRect.top){    //下拉
                Animation animation = new TranslateAnimation(0, 0,  contentView.getTop()- HEADER_SPRING_HEIGHT,mRect.top);
                animation.setDuration(MOVE_TIME_OVER);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        callOnAfterRefreshAnim();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                contentView.startAnimation(animation);
                contentView.layout(mRect.left, mRect.top + HEADER_SPRING_HEIGHT, mRect.right, mRect.bottom+ HEADER_SPRING_HEIGHT);
            }else {     //上拉
                Animation animation = new TranslateAnimation(0, 0,  contentView.getTop() + FOOTER_SPRING_HEIGHT,mRect.top);
                animation.setDuration(MOVE_TIME_OVER);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        callOnAfterRefreshAnim();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                contentView.startAnimation(animation);
                contentView.layout(mRect.left, mRect.top - FOOTER_SPRING_HEIGHT, mRect.right, mRect.bottom - FOOTER_SPRING_HEIGHT);
            }
        }else if(type== Type.FOLLOW){
            if (getScrollY()<0) {     //下拉
                mScroller.startScroll(0, getScrollY(), 0, -getScrollY() - HEADER_SPRING_HEIGHT, MOVE_TIME);
                invalidate();
            } else {       //上拉
                mScroller.startScroll(0, getScrollY(), 0, -getScrollY()+ FOOTER_SPRING_HEIGHT,MOVE_TIME);
                invalidate();
            }
        }
    }

    public void callFresh(){
        header.setVisibility(VISIBLE);
        if (type== Type.OVERLAP){
            if (mRect.isEmpty()) {
                mRect.set(contentView.getLeft(), contentView.getTop(),contentView.getRight(), contentView.getBottom());
            }

            Animation animation = new TranslateAnimation(0, 0,  contentView.getTop()- HEADER_SPRING_HEIGHT,mRect.top);
            animation.setDuration(MOVE_TIME_OVER);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (mHeader!=null) mHeader.onStartAnim();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    callFreshORload = 1;
                    needResetAnim = true;
                    listener.onRefresh();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            contentView.startAnimation(animation);
            contentView.layout(mRect.left, mRect.top + HEADER_SPRING_HEIGHT, mRect.right, mRect.bottom+ HEADER_SPRING_HEIGHT);
        }else if(type== Type.FOLLOW){
            isFullAnim = false;
            hasCallRefresh = false;
            callFreshORload = 1;
            needResetAnim = true;
            if (mHeader!=null) mHeader.onStartAnim();
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY() - HEADER_SPRING_HEIGHT, MOVE_TIME);
            invalidate();
        }
    }

    /**
     * 智能判断是重置控件位置到初始状态还是到刷新/加载状态
     */
    private void restSmartPosition(){
        if (listener==null){
            resetPosition();
        }else {
            if ( isTopOverFarm()){
                callFreshORload();
                if (give == Give.BOTH|| give == Give.TOP)
                    resetRefreshPosition();
                else
                    resetPosition();
            }else if( isBottomOverFarm()){
                callFreshORload();
                if (give == Give.BOTH|| give == Give.BOTTOM)
                    resetRefreshPosition();
                else
                    resetPosition();
            }
            else {
                resetPosition();
            }
        }
    }

    private void callFreshORload(){
        if (isTop()) {  //下拉
            callFreshORload = 1;
            if (type== Type.OVERLAP){
                if (dsY>200 || HEADER_LIMIT_HEIGHT>=HEADER_SPRING_HEIGHT){
                    if (mHeader!=null) mHeader.onStartAnim();
                }
            }else if(type== Type.FOLLOW){
                if (mHeader!=null) mHeader.onStartAnim();
            }
        } else if (isBottom()) {
            callFreshORload = 2;
            if (type== Type.OVERLAP){
                if (dsY<-200 || FOOTER_LIMIT_HEIGHT>=FOOTER_SPRING_HEIGHT){
                    if (mFooter!=null) mFooter.onStartAnim();
                }
            }else if(type== Type.FOLLOW){
                if (mFooter!=null) mFooter.onStartAnim();
            }
        }
    }

    private boolean isChildScrollToTop() {
        return !ViewCompat.canScrollVertically(contentView, -1);
    }

    private boolean isChildScrollToBottomFull(boolean isFull) {
        return !ViewCompat.canScrollVertically(contentView, 1);
    }

    private boolean isChildScrollToBottom(){
        return isChildScrollToBottomFull(true);
    }

    private boolean isFullScrean(){
        boolean isBottom = isChildScrollToBottomFull(false);
        if (isBottom){
            return isChildScrollToBottomFull(true);
        }
        return true;
    }

    /**
     * 判断顶部拉动是否超过临界值
     */
    private boolean isTopOverFarm(){
        if (type== Type.OVERLAP){
            return contentView.getTop()> HEADER_LIMIT_HEIGHT;
        }else if(type== Type.FOLLOW){
            return -getScrollY()> HEADER_LIMIT_HEIGHT;
        }else
            return false;
    }

    /**
     * 判断底部拉动是否超过临界值
     */
    private boolean isBottomOverFarm(){
        if (type== Type.OVERLAP){
            return getHeight()-contentView.getBottom()> FOOTER_LIMIT_HEIGHT;
        }else if(type== Type.FOLLOW){
            return getScrollY()> FOOTER_LIMIT_HEIGHT;
        }else
            return false;
    }

    /**
     * 判断当前状态是否拉动顶部
     */
    private boolean isTop(){
        if (type== Type.OVERLAP){
            return contentView.getTop()>0;
        }else if (type== Type.FOLLOW){
            return getScrollY()<0;
        }else
            return false;
    }
    private boolean isBottom(){
        if (type== Type.OVERLAP){
            return contentView.getTop()<0;
        }else if(type== Type.FOLLOW){
            return getScrollY()>0;
        }else
            return false;
    }
    private boolean isFlow(){
        if (type== Type.OVERLAP){
            return contentView.getTop()<30 && contentView.getTop()>-30;
        }else if (type== Type.FOLLOW){
            return getScrollY()>-30 && getScrollY()<30;
        }else
            return false;
    }

    /**
     * 切换Type的方法，之所以不暴露在外部，是防止用户在拖动过程中调用造成布局错乱
     * 所以在外部方法中设置标志，然后在拖动完毕后判断是否需要调用，是则调用
     */
    private void changeType(Type type){
        this.type = type;
        if (header!=null&& header.getVisibility()!=INVISIBLE) header.setVisibility(INVISIBLE);
        if (footer!=null&& footer.getVisibility()!=INVISIBLE) footer.setVisibility(INVISIBLE);
        requestLayout();
        needChange = false;
    }

    //#############################################
    //##            对外暴露的方法               ##
    //#############################################

    /**
     * 重置控件位置，暴露给外部的方法，用于在刷新或者加载完成后调用
     */
    public void onFinishFreshAndLoad(){
        if (!isMoveNow && needResetAnim) {
            boolean needTop = isTop() && (give == Give.TOP || give == Give.BOTH);
            boolean needBottom = isBottom() && (give == Give.BOTTOM || give == Give.BOTH);
            if (needTop || needBottom) {
                if (contentView instanceof ListView) {
                    //((ListView) contentView).smoothScrollByOffset(1);
                    //刷新后调用，才能正确显示刷新的item，如果调用上面的方法，listview会被固定在底部
//                    ((ListView) contentView).smoothScrollBy(-1,0);
                }
                resetPosition();
            }
        }
    }

    public void setMoveTime(int time){
        this.MOVE_TIME = time;
    }
    public void setMoveTimeOver(int time){
        this.MOVE_TIME_OVER = time;
    }

    /**
     * 是否禁用SpringView
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    /**
     * 设置监听
     */
    public void setListener(OnFreshListener listener) {
        this.listener = listener;
    }

    /**
     * 动态设置弹性模式
     */
    public void setGive(Give give) {
        this.give = give;
    }

    /**
     * 改变样式的对外接口
     */
    public void setType(Type type) {
        if (isTop()||isBottom()){
            //如果当前用户正在拖动，直接调用changeType()会造成布局错乱
            //设置needChange标志，在执行完拖动后再调用changeType()
            needChange = true;
            //把参数保持起来
            _type = type;
        }else {
            changeType(type);
        }
    }













    /***************************************TEST*******************************************/

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if(getChildCount()>3){
            throw new IllegalArgumentException("ChildCount Cannot over 3");
        }
        if(child instanceof UIHandler){
            UIHandler UIHandler = (UIHandler) child;
            if(UIHandler.isHeader()){
                mHeader= UIHandler;
            }else {
                mFooter= UIHandler;
            }
        }
    }
    UIHandler mHeader,mFooter;
    @Override
    protected void onFinishInflate() {
        int count = getChildCount();
        if(count >3){
            throw new IllegalArgumentException("ChildCount Cannot over 3");
        }
        setPadding(0,0,0,0);
        UIHandler UIHandler;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if(view instanceof UIHandler){
                UIHandler = (UIHandler) view;
                if(UIHandler.isHeader()){
                    if(mHeader!=null){
                        throw new IllegalArgumentException("can only on header");
                    }
                    mHeader= UIHandler;
                    header=view;
                }else {
                    if(mFooter!=null){
                        throw new IllegalArgumentException("can only one footer");
                    }
                    mFooter= UIHandler;
                    footer=view;
                }
            }else {
                contentView = view;
                contentView.setPadding(0,0,0,0);
                contentView.bringToFront();
            }
        }
        super.onFinishInflate();
    }

    /********************************************************************************************/



















    /**
     * 获取当前样式
     */
    public Type getType() {
        return type;
    }
    public View getHeaderView(){
        return header;
    }
    public View getFooterView(){
        return footer;
    }
    private boolean needChangeHeader = false;
    private boolean needChangeFooter = false;
    public UIHandler getHeader(){
        return mHeader;
    }
    public UIHandler getFooter(){
        return mFooter;
    }
    public void setHeader(UIHandler mHeader) {
        if (this.mHeader!=null&&isTop()){
            needChangeHeader = true;
            resetPosition();
        }else {
            setHeaderIn(mHeader);
        }
    }
    private void setHeaderIn(UIHandler mHeader){
        this.mHeader = mHeader;
        if (header != null) {
            removeView(this.header);
        }
        addView((View) mHeader,new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        contentView.bringToFront(); //把内容放在最前端
        requestLayout();
    }
    public void setFooter(UIHandler footerHander) {
        if (this.mFooter!=null&&isBottom()){
            needChangeFooter = true;
            resetPosition();
        }else {
            setFooterIn(footerHander);
        }
    }
    private void setFooterIn(UIHandler footerHander) {
        if (footer!=null){
            removeView(footer);
        }
        addView((View) footerHander,new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        contentView.bringToFront(); //把内容放在最前端
        requestLayout();
    }
}
