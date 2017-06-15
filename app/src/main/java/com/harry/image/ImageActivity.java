/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.harry.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 * 图片压缩
 * @author Harry
 * @date 2016/11/1.
 */
public class ImageActivity extends Activity {
    @BindView(R.id.iv_origin)
    ImageView mOrigin;
    @BindView(R.id.iv_new)
    ImageView mNew;

    StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Bitmap result = compress();
        Log.e(getClass().getSimpleName(), result.getByteCount() + " quality byte");
        mNew.setImageBitmap(result);
    }

    private Bitmap compress() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//此时decode bitmap=null
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic1, options);
        Log.e(getClass().getSimpleName(), options.outWidth + "width*height="+options.outHeight);
        options.inSampleSize = calculateInSampleSize(options, 800, 480);
        options.inJustDecodeBounds = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic1, options);
        Log.e(getClass().getSimpleName(), "size"+bitmap.getWidth() +
            "width*height="+bitmap.getHeight());//960*540
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap res = BitmapFactory.decodeStream(bais, null, null);
        return res;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;//压缩比例
        int height = options.outHeight;
        int width = options.outWidth;
        if (height > reqHeight || width > reqWidth) {
            int hRatio = Math.round((float) height / reqHeight);
            int wRatio = Math.round((float) width / reqWidth);
            inSampleSize = hRatio < wRatio ? hRatio : wRatio;
        }
        return inSampleSize;
    }
}
