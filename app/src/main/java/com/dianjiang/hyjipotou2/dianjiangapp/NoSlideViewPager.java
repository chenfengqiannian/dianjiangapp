package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hyjipotou2 on 16/4/13.
 */
public class NoSlideViewPager extends ViewPager {

    private boolean isCanSlide=false;
    public NoSlideViewPager(Context context) {
        super(context);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanSlide(boolean isCanSlide){
        this.isCanSlide=isCanSlide;
    }

    @Override
    public void scrollTo(int x, int y){
     //if (isCanSlide){
        super.scrollTo(x, y);
        //}
    }

    @Override
    public void setCurrentItem(int item) {
// TODO Auto-generated method stub
        super.setCurrentItem(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
// TODO Auto-generated method stub
        if (isCanSlide) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
// TODO Auto-generated method stub
        if (isCanSlide) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }

}