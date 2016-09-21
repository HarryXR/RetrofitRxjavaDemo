/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/21.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
