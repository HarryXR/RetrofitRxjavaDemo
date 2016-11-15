/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.harry.R;
import com.harry.mvp.model.DownloadController;
import com.harry.mvp.presenter.DownLoadPresenterImpl;
import com.harry.mvp.view.IDownLoadView;
import com.harry.rv.rxretrofit.model.BaseRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/15.
 */
public class DownloadActivity extends Activity implements IDownLoadView<InputStream>{
    ImageView iv;
    DownLoadPresenterImpl mPresenter;
    DownloadController mController;
    File dir= Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        iv = (ImageView) findViewById(R.id.iv_main);
        mController=new DownloadController(this);
        mPresenter=new DownLoadPresenterImpl(mController,this);
        mPresenter.load(new BaseRequest());
    }

    @Override
    public void onSuccess(InputStream out) {
        byte[] buf = new byte[4096];
        FileOutputStream fos = null;
        int length;
        File file;
        try {
            long sum = 0;
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file=new File(dir,"1.jpg");
            fos = new FileOutputStream(file);
            while ((length =out.read(buf)) != -1) {
                sum += length;
                fos.write(buf, 0, length);
            }
            fos.flush();
            FileInputStream fis = new FileInputStream(file);
            iv.setImageBitmap(BitmapFactory.decodeStream(fis));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }

        }
    }

    @Override
    public void onError() {

    }
}
