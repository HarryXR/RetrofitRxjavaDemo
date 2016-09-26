/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.rxretrofit.controller;

import com.harry.rv.rxretrofit.model.BaseResponse;
import com.harry.rv.rxretrofit.model.PostResponse;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class UploadController extends HttpClient<UploadController.LoadListener> {

    public UploadController(LoadListener l) {
        super(l);
    }

    public void load(RequestBody request) {
        LoadTask task = new LoadTask();
        task.load(request);
    }

    private class LoadTask extends BaseTask<RequestBody,PostResponse> {

        @Override
        public Observable<BaseResponse<PostResponse>> getObservable() {
            return service.upload(input);
        }

        @Override
        public void onSuccess(PostResponse out) {
            listener.onSuccess(out);
        }

        @Override
        public void onErrors(Throwable error) {
            listener.onError(error);
        }

        @Override
        public void onComplete() {
            listener.onComplete();
        }
    }

    public interface LoadListener {
        void onSuccess(PostResponse out);

        void onError(Throwable error);

        void onComplete();
    }
}