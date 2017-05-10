/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.model;

import android.content.Context;

import com.harry.mvp.presenter.DownLoadPresenterImpl;
import com.harry.mvp.presenter.ITopPresenter;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/15. http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg
 */
public class DownloadController extends HttpManager {

    public DownloadController(Context context) {
        super(context);
    }
    public void load(DownLoadPresenterImpl presenter){
        LoadTask task=new LoadTask();
        task.load(presenter);
    }

    private class LoadTask {
        Observable<ResponseBody> observable;
        ITopPresenter presenter;

        protected void load(final ITopPresenter presenter) {

            this.presenter = presenter;
            observable = getObservable();
            observable.map(new DownloadFunc()).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<byte[]>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    presenter.error();
                }

                @Override
                public void onNext(byte[] res) {
                    presenter.success(res);
                }
            });
        }



        public Observable<ResponseBody> getObservable() {
            return service.download("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
        }
    }

}
