package com.harry;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.harry.anno.AnnotationActivity;
import com.harry.image.ImageActivity;
import com.harry.mvp.DownloadActivity;
import com.harry.mvp.TopViewActivity;
import com.harry.reflect.ReflectActivity;
import com.harry.rxjava.RxJavaActivity;
import com.harry.rxjava.UploadActivity;
import com.harry.rxjava.retrofit.TopActivity;
import com.harry.service.binder.BinderActivity;
import com.harry.slide.ViewPagerActivity;
import com.harry.socket.ClientActivity;
import com.harry.video.MovieRecorderActivity;
import com.harry.video.VideoActivity;

import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionGen.with(this).permissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE).request();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return;
            }
        }
    }

    @Override
    protected Class[] getActivities() {
        return new Class[]{RxJavaActivity.class, TopActivity.class, UploadActivity.class, VideoActivity.class,
            MovieRecorderActivity.class, ProgressActivity.class, TopViewActivity.class, ViewPagerActivity.class,
            ReflectActivity.class, ImageActivity.class, AnnotationActivity.class, DownloadActivity.class,
            ImmersiveActivity.class, AccessActivity.class, AesRsaActivity.class, ClientActivity.class, BinderActivity
            .class,DayNightActivity.class,WebViewActivity.class};
    }
}
