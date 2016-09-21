/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/8/17.
 */
public class TestViewpager extends ViewPager {

    public boolean isCanScroll=true;
    public TestViewpager(Context context) {
        this(context,null);
    }

    public TestViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isCanScroll){
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(!isCanScroll){
            return false;
        }
        return super.onInterceptHoverEvent(event);
    }

//    @Override
//    public void scrollTo(int x, int y) {
//        if(isCanScroll){
//        super.scrollTo(x, y);
//        }
//    }
}
