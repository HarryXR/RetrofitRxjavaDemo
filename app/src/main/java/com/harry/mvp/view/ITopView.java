/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.view;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/24.
 */
public interface ITopView<T> {
    void onSuccess(T out);
    void onError();
}
