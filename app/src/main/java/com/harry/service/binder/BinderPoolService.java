/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.service.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/24.
 */
public class BinderPoolService extends Service {

    private Binder mBinderPool = new BinderPool.BinderPoolImpl();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}
