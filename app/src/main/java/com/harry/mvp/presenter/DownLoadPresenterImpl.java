/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.presenter;

import com.harry.mvp.model.DownloadController;
import com.harry.mvp.view.IDownLoadView;
import com.harry.rv.rxretrofit.model.BaseRequest;

import java.io.InputStream;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/15.
 */
public class DownLoadPresenterImpl implements ITopPresenter<BaseRequest, InputStream> {
    DownloadController mController;
    IDownLoadView<InputStream> mView;

    public DownLoadPresenterImpl(DownloadController controller, IDownLoadView view) {
        this.mController = controller;
        this.mView = view;
    }

    @Override
    public void load(BaseRequest request) {
        mController.load(this);
    }

    @Override
    public void success(InputStream out) {

        mView.onSuccess(out);
    }

    @Override
    public void error() {
        mView.onError();
    }
}
