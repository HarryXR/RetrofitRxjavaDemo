/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.rxjava.retrofit;

import java.io.Serializable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/19.
 */
public class BaseResponse<T> implements Serializable {
    public int count;
    public int start;
    public int total;
    T subjects;
}
