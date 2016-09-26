/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.harry.rv.rxretrofit.controller.UploadController;
import com.harry.rv.rxretrofit.model.PostResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/26.
 */
public class UploadActivity extends Activity implements UploadController.LoadListener {
    UploadController mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1463713109016.jpg");
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        mController = new UploadController(this);
        mController.load(requestBody1);
    }

    @Override
    public void onSuccess(PostResponse out) {
        Log.e(this.getLocalClassName(),out.baseurl);
    }

    @Override
    public void onError(Throwable error) {
        Log.e(this.getLocalClassName(),"失败");
    }

    @Override
    public void onComplete() {

    }
}
