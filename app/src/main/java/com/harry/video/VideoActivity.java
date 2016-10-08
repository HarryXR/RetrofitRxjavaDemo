/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.video;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.harry.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/8.
 */
public class VideoActivity extends AppCompatActivity {
    @BindView(R.id.jc_player)
    JCVideoPlayerStandard mPLayer;

    JCVideoPlayer.JCAutoFullscreenListener sensorListener;
    SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorListener=new JCVideoPlayer.JCAutoFullscreenListener(){
            @Override
            public void onSensorChanged(SensorEvent event) {
                super.onSensorChanged(event);
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                if (x < -10) {
                    //direction right
                } else if (x > 5) {
                    //direction left
                    if (JCVideoPlayerManager.getFirst() != null) {
                        JCVideoPlayerManager.getFirst().autoFullscreenLeft();
//                        mPLayer.startWindowFullscreen();
                    }
                } else if (y > 5) {
                    if (JCVideoPlayerManager.getFirst() != null) {
                        JCVideoPlayerManager.getFirst().autoQuitFullscreen();
//                        mPLayer.autoQuitFullscreen();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                super.onAccuracyChanged(sensor, accuracy);
            }
        };

        mPLayer.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
            , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "嫂子闭眼睛");

//        mPLayer.startWindowFullscreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(sensorListener,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        mSensorManager.unregisterListener(sensorListener);
    }

}
