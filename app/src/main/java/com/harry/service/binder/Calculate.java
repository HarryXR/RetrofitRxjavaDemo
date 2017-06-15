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
public class Calculate extends ICalculate.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
