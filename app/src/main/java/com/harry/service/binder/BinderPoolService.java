/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.service.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/24.
 */
public class BinderPoolService extends Service {

    private Binder mBinderPool = new BinderPool.BinderPoolImpl();

    @Override
    public void onCreate() {
        Log.e(getClass().getSimpleName(),Thread.currentThread()+"");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(),Thread.currentThread()+"");
        return mBinderPool;
    }
}
