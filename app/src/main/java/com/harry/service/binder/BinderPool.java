/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.service.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/24.
 */
public class BinderPool {
    public static final int BINDER_SPEAK = 1;
    public static final int BINDER_CALCULATE = 2;

    private Context mContext;
    private static IBinderPool mIBinderPool;
    private static BinderPool mBinderPool;

    private CountDownLatch mCountDownLatch;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectService();
    }

    public static BinderPool getInstance(Context context) {
        if (mIBinderPool == null) {
            synchronized (BinderPool.class) {
                if (mIBinderPool == null) {
                    mBinderPool = new BinderPool(context);
                }
            }
        }
        return mBinderPool;
    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = (IBinderPool) service;
            try {
                mIBinderPool.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mIBinderPool.asBinder().unlinkToDeath(deathRecipient, 0);
            mIBinderPool = null;
            connectService();
        }
    };

    private synchronized void connectService() {
        mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent("com.harry.service.binder.BinderPoolService");
        intent.setPackage("com.harry.rx");
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;
        if (mIBinderPool != null){
            try {
                binder = mIBinderPool.query(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }


    public static class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super();
        }


        @Override
        public IBinder query(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_CALCULATE:
                    binder = new Calculate();
                    break;
                case BINDER_SPEAK:
                    binder = new Speak();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
