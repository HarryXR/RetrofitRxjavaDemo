/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.harry.rv.rxretrofit.controller.UploadController;
import com.harry.rv.rxretrofit.model.BaseRequest;
import com.harry.rv.rxretrofit.model.PostResponse;

import java.io.File;

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
        mController = new UploadController(this);
        mController.save(new BaseRequest(),file);
    }

    @Override
    public void onSuccess(PostResponse out) {
        Log.e(this.getLocalClassName(),out.baseurl);
        //09-28 10:49:17.163 12658-12658/com.harry E/rxjava.UploadActivity: http://img-test.mainaer.com/uploads/user/photos/2016-09-28/612f4bc08167cf9ceda670baac3fd0ecefe9c8ad.jpg
    }

    @Override
    public void onError(Throwable error) {
        Log.e(this.getLocalClassName(),"失败");
    }

    @Override
    public void onComplete() {

    }
}
