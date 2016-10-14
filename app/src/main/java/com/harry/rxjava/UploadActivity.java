/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rxjava;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.R;
import com.harry.rv.rxretrofit.controller.UploadController;
import com.harry.rv.rxretrofit.model.BaseRequest;
import com.harry.rv.rxretrofit.model.PostResponse;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/26.
 */
public class UploadActivity extends Activity implements UploadController.LoadListener {
    UploadController mController;
    @BindView(R.id.iv)
    SimpleDraweeView mIv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        File file = new File("/storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1463713109016.jpg");
        mController = new UploadController(this,this);
        mController.save(new BaseRequest(), file);
    }

    @Override
    public void onSuccess(PostResponse out) {
        Log.e(this.getLocalClassName(), out.baseurl);
        mIv.setImageURI(Uri.parse(out.baseurl));

    }

    @Override
    public void onError(Throwable error) {
        Log.e(this.getLocalClassName(), "失败");
    }

    @Override
    public void onComplete() {

    }
}
