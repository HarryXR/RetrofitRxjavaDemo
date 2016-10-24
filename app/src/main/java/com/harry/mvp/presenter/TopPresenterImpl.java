/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.presenter;

import com.harry.mvp.view.ITopView;
import com.harry.rv.rxretrofit.model.MovieResponse;

import java.util.List;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/24.
 */
public class TopPresenterImpl implements ITopPresenter<List<MovieResponse>> {
    ITopView topView;

    public TopPresenterImpl(ITopView topView) {
        this.topView = topView;
    }

    @Override
    public void load() {

    }

    @Override
    public void success(List<MovieResponse> out) {
        topView.onSuccess(out);
    }

    @Override
    public void error() {
        topView.onError();
    }
}
