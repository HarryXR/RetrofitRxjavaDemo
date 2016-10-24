/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.presenter;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/24.
 */
public interface ITopPresenter<T> {
    void load();
    void success(T out);
    void error();
}
