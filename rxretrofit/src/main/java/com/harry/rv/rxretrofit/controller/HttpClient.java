/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.rxretrofit.controller;

import com.harry.rv.rxretrofit.api.MovieService;
import com.harry.rv.rxretrofit.model.BaseResponse;
import com.harry.rv.rxretrofit.retrofit.BaseInterceptor;
import com.harry.rv.rxretrofit.retrofit.HttpResultFunc;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class HttpClient<L> {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";//http://api-test.mainaer.com/v3.0/
    Retrofit retrofit;
    MovieService service;

    L listener;

    public HttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(5, TimeUnit.SECONDS);
        client.addInterceptor(new BaseInterceptor());
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client.build()).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        service = retrofit.create(MovieService.class);
    }

    public void setListener(L l) {
        this.listener = l;
    }

    public HttpClient(L l) {
        this();
        setListener(l);
    }

    protected abstract class BaseTask<Input, T> {

        Observable<BaseResponse<T>> observable;
        Input input;

        protected void load(Input input) {
            this.input = input;
            observable = getObservable();
            observable.map(new HttpResultFunc<T>()).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    onComplete();
                }

                @Override
                public void onError(Throwable e) {
                    onErrors(e);
                }

                @Override
                public void onNext(T res) {
                    onSuccess(res);
                }
            });
        }

        protected Observable<BaseResponse<T>> getObservable() {
            return null;
        }

        public abstract void onSuccess(T out);

        public abstract void onErrors(Throwable error);

        public abstract void onComplete();
    }

}
