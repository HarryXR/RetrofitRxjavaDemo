/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.model;

import android.content.Context;

import com.harry.mvp.presenter.TopPresenterImpl;
import com.harry.rv.rxretrofit.model.BaseResponse;
import com.harry.rv.rxretrofit.model.MovieResponse;
import com.harry.rv.rxretrofit.retrofit.MovieRequest;

import java.util.List;

import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class TopController extends HttpManager {

    LoadTask task;
    public TopController(Context context) {
        super(context);
    }

    public void load(MovieRequest request, TopPresenterImpl presenter) {
        task = new LoadTask();
        task.load(request,presenter);
    }

    private class LoadTask extends BaseTask<MovieRequest,List<MovieResponse>> {

        @Override
        public Observable<BaseResponse<List<MovieResponse>>> getObservable() {
            return service.getMovie(input.start);
        }
    }

    @Override
    protected void cancel() {
        task.cancel();
    }
}