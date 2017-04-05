/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日夜间模式切换
 *
 * @author Harry
 * @date 2017/3/7.
 */
public class DayNightActivity extends AppCompatActivity implements View.OnClickListener {
    
    @BindView(R.id.btn_day)
    View mDay;
    @BindView(R.id.btn_night)
    View mNight;
    @BindView(R.id.btn_light)
    View mLight;
    @BindView(R.id.btn_add)
    View mAdd;
    
    private Camera camera;
    private Camera.Parameters parameters;
    
    private WindowManager mWindowManager;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
        ButterKnife.bind(this);
        mDay.setOnClickListener(this);
        mNight.setOnClickListener(this);
        mLight.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        camera = Camera.open();
    }
    
    private boolean status = false;
    
    @Override
    public void onClick(View v) {
        if (v == mDay) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
        else if (v == mNight) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        }
        else if (v == mLight) {
            if (!status) {
                status = true;
                new Thread(new TurnOnLight()).start();
            }
            else {
                status = false;
                parameters.setFlashMode("off");
                camera.setParameters(parameters);
            }
        }
        else if (v == mAdd) {
            if(floatBtn != null){
                return;
            }
            addView();
        }
    }
    Button floatBtn;
    private void addView() {
        floatBtn = new Button(this);
        floatBtn.setText("float button");
        LayoutParams layoutParams = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);//0,0 分别是type和flags参数，在后面分别配置了
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
            | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = 100;
        layoutParams.y = 300;
//        floatBtn.setOnTouchListener(this);
        mWindowManager.addView(floatBtn, layoutParams);
    }
    
    private class TurnOnLight implements Runnable {
        @Override
        public void run() {
            parameters = camera.getParameters();
            parameters.setFlashMode("torch");
            camera.setParameters(parameters);
        }
    }
    
    @Override
    protected void onDestroy() {
        if(floatBtn != null){
            mWindowManager.removeViewImmediate(floatBtn);
//            mWindowManager.removeView(floatBtn);//do later
        }
        super.onDestroy();
    }
}
