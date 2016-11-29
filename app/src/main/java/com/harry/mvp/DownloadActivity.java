/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class DownloadActivity extends Activity implements IDownLoadView<InputStream>, View.OnClickListener {
    ImageView iv;
    ImageView end;
    Button start;
    DownLoadPresenterImpl mPresenter;
    DownloadController mController;
    File dir = Environment.getExternalStorageDirectory();

    public static int[] location_download = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        iv = (ImageView) findViewById(R.id.iv_main);
        end = (ImageView) findViewById(R.id.end);
        start=(Button)findViewById(R.id.start);
        mController = new DownloadController(this);
        mPresenter = new DownLoadPresenterImpl(mController, this);
        mPresenter.load(new BaseRequest());
        start.setOnClickListener(this);
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
            file = new File(dir, "2.jpg");
            fos = new FileOutputStream(file);
            while ((length = out.read(buf)) != -1) {
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

    @Override
    public void onClick(View v) {
        if (v == start) {
            startAnim();
        }
    }

    private void startAnim() {
        iv.setDrawingCacheEnabled(true);
        Bitmap bitmap = iv.getDrawingCache();
        ImageView logo = new ImageView(this);
        logo.setScaleType(ImageView.ScaleType.FIT_XY);
        logo.setImageBitmap(bitmap);
        int[] startLocation = new int[2];
        iv.getLocationInWindow(startLocation);
        end.getLocationInWindow(location_download);
        setAnim(logo, startLocation, location_download);
    }

    private void setAnim(final ImageView logo, int[] startLocation, int[] location_download) {
        ViewGroup animMaskLayout = createAnimLayout();
        animMaskLayout.addView(logo);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(logo, startLocation);

        // 计算位移
        int endX = location_download[0] - startLocation[0];
        ;// 动画位移的X坐标
        int endY = location_download[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                logo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.GONE);
            }
        });
    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private static View addViewToAnimLayout(final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(80, 80);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
}
