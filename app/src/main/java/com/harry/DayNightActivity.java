/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日夜间模式切换
 *
 * @author Harry
 * @date 2017/3/7.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DayNightActivity extends AppCompatActivity implements View.OnClickListener {
    
    @BindView(R.id.btn_day)
    View mDay;
    @BindView(R.id.btn_night)
    View mNight;
    @BindView(R.id.btn_light)
    View mLight;
    @BindView(R.id.btn_add)
    TextView mAdd;
    private final static String TAG="DayNightActivity";
    private Camera camera;
    private Camera.Parameters parameters;
    
    private WindowManager mWindowManager;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
        ButterKnife.bind(this);
        mDay.setOnClickListener(this);
        mNight.setOnClickListener(this);
        mLight.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mAdd.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
        Log.e(TAG, mAdd.getLayout().getLineEnd(0)+"");
            }
        });
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        initCamera2();
        Log.e(TAG,getClassLoader().toString());
    }
    
    private CameraManager manager;
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession = null;
    private CaptureRequest request = null;
    private SurfaceTexture surfaceTexture;
    private Surface surface;
    private boolean status = false;
    
    String cameraId = null;
    private final CameraCaptureSession.StateCallback stateCallback = new CameraCaptureSession.StateCallback() {
        
        public void onConfigured(CameraCaptureSession arg0) {
            captureSession = arg0;
            CaptureRequest.Builder builder;
            try {
                builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                builder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                builder.addTarget(surface);
                request = builder.build();
                captureSession.capture(request, null, null);
            } catch (CameraAccessException e) {
                Log.e(TAG, e.getMessage());
            }
        };
        
        public void onConfigureFailed(CameraCaptureSession arg0) {
        };
    };
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void openCamera2Flash() throws CameraAccessException {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.openCamera(cameraId, new CameraDevice.StateCallback() {
            
            @Override
            public void onOpened(CameraDevice camera) {
                cameraDevice = camera;
                createCaptureSession();
            }
            
            @Override
            public void onError(CameraDevice camera, int error) {
            }
            
            @Override
            public void onDisconnected(CameraDevice camera) {
            }
        }, null);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createCaptureSession() {
        this.surfaceTexture = new SurfaceTexture(0, false);
        this.surfaceTexture.setDefaultBufferSize(1280, 720);
        this.surface = new Surface(this.surfaceTexture);
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(this.surface);
        try {
            this.cameraDevice.createCaptureSession(localArrayList, stateCallback, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
        try {
            for (String cameraId : this.manager.getCameraIdList()) {
                CameraCharacteristics characteristics = this.manager.getCameraCharacteristics(cameraId);
                // 过滤掉前置摄像头
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }
                this.cameraId = cameraId;
                // 判断设备是否支持闪光灯
//                this.isSupportFlashCamera2 = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    
    @Override
    public void onClick(View v) {
        if (v == mDay) {
            //noinspection WrongConstant
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
        else if (v == mNight) {
            //noinspection WrongConstant 不检验错误常数
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        }
        else if (v == mLight) {
            if (!status) {
                status = true;
                try {
                    openCamera2Flash();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                status=false;
                if(cameraDevice != null){
                    cameraDevice.close();
                }
            }
         
        }
        else if (v == mAdd) {
            if(flaot != null){
                return;
            }
            addView();
        }else if(v == phone){
            if(phone != null){
                phone.setVisibility(View.GONE);
            }
            Toast.makeText(this,"打电话",Toast.LENGTH_SHORT).show();
        }
    }
    View phone;
    View flaot;
    private void addView() {
        flaot=View.inflate(this,R.layout.float_layout,null);
        phone=flaot.findViewById(R.id.phone);
        phone.setOnClickListener(this);
        LayoutParams layoutParams = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);//0,0 分别是type和flags参数，在后面分别配置了
        layoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
            | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = LayoutParams.TYPE_PHONE;
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
//        layoutParams.x = 100;
//        layoutParams.y = 300;
//        floatBtn.setOnTouchListener(this);
        mWindowManager.addView(flaot, layoutParams);
    }
    
    @Override
    protected void onDestroy() {
        if(flaot != null){
            mWindowManager.removeViewImmediate(flaot);
//            mWindowManager.removeView(floatBtn);//do later
        }
        super.onDestroy();
    }
}
