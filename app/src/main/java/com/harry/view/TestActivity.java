/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.harry.test.R;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/7/20.
 */
public class TestActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private TestLinearLayout mLayout;
    private TestButton mBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        mLayout = (TestLinearLayout) findViewById(R.id.layout);
//        mBtn = (TestButton) findViewById(R.id.btn);
//        mLayout.setOnTouchListener(this);
//        mBtn.setOnTouchListener(this);
//
//        mLayout.setOnClickListener(this);
//        mBtn.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(v.getClass().getSimpleName(), "OnTouchListener--onTouch-- action=" + event.getAction() + " --" + v);
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.e(v.getClass().getSimpleName(), "OnClickListener--onClick--" + v);
    }
}
