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
public interface ITopPresenter<Input,T> {
    void load(Input input);
    void success(T out);
    void error();
}
