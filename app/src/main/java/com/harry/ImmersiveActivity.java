/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * 沉浸式界面，类似饿了么
 * 仅支持5.0及以上
 *
 * @author Harry
 * @date 2016/11/28.
 */
public class ImmersiveActivity extends Activity {
    ImageView iv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        iv = (ImageView) findViewById(R.id.iv);
        //ImageView的闪烁动画
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation1.setDuration(500);
        alphaAnimation1.setRepeatCount(1);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);//反转
        iv.startAnimation(alphaAnimation1);
        alphaAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                iv.setBackgroundResource(R.mipmap.ic_launcher);
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //适合游侠模式下使用
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }
}
