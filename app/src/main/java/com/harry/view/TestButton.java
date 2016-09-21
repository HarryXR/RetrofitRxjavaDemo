/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/7/20.
 */
public class TestButton extends Button {
    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(this.getClass().getSimpleName(), "TestButton dispatchTouchEvent-- action=" + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(this.getClass().getSimpleName(), "TestButton onTouchEvent-- action=" + event.getAction());
        return super.onTouchEvent(event);
    }
}
