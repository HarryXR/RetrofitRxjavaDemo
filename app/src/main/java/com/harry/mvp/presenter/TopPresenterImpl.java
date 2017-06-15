/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.presenter;

import com.harry.mvp.model.TopController;
import com.harry.mvp.view.ITopView;
import com.harry.rv.rxretrofit.model.MovieResponse;
import com.harry.rv.rxretrofit.retrofit.MovieRequest;

import java.util.List;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/24.
 */
public class TopPresenterImpl implements ITopPresenter<MovieRequest,List<MovieResponse>> {
    ITopView topView;
    TopController mController;

    public TopPresenterImpl(ITopView topView,TopController controller) {
        this.topView = topView;
        mController=controller;
    }

    @Override
    public void load(MovieRequest req) {
        mController.load(req,this);
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
