/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.service.binder;

import android.os.RemoteException;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/24.
 */
public class Speak extends ISpeak.Stub{
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble,
                           String aString) throws RemoteException {

    }

    @Override
    public String speak() throws RemoteException {
        return "I`m speaking...";
    }
}
