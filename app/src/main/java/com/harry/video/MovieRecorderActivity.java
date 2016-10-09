/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.video;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.harry.R;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/9.
 */
public class MovieRecorderActivity extends AppCompatActivity {

    private MovieRecorderView movieRV;
    private Button startBtn;
    private Button stopBtn;

    private Button playBtn;
    private Button pauseBtn;

    private SurfaceView playView;
    private MediaPlayer player;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_recorder);
        initViews();
        initEvents();
        init();

    }
    private void init()
    {
        player=new MediaPlayer();
        playView=(SurfaceView) this.findViewById(R.id.play_surfaceV);

        //设置SurfaceView自己不管理的缓冲区
        playView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        playView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (position>0) {
                    try {
                        //开始播放
                        play();
                        //并直接从指定位置开始播放
                        player.seekTo(position);
                        position=0;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {

            }
        });
    }
    private void initViews()
    {
        movieRV=(MovieRecorderView)findViewById(R.id.moive_rv);
        //录制
        startBtn=(Button)findViewById(R.id.start_btn);
        stopBtn=(Button)findViewById(R.id.stop_btn);
        //播放
        playBtn=(Button)findViewById(R.id.play_btn);
        pauseBtn=(Button)findViewById(R.id.pause_btn);
    }

    private void initEvents()
    {
        //开始录制
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieRV.record(new MovieRecorderView.OnRecordFinishListener() {
                    @Override
                    public void onRecordFinish() {

                    }
                });
            }
        });
        //停止录制
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieRV.stop();
            }
        });
        //播放已录制视频
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        //暂停
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying())
                {
                    player.pause();
                }
                else
                {
                    player.start();
                }
            }
        });


    }
    @Override
    protected void onPause() {
        //先判断是否正在播放
        if (player.isPlaying()) {
            //如果正在播放我们就先保存这个播放位置
            position=player.getCurrentPosition()
            ;
            player.stop();
        }
        super.onPause();
    }
    private void play()
    {
        try {
            Log.d("play:","");
            player.reset();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置需要播放的视频
            String path=movieRV.getmVecordFile().getAbsolutePath();
            player.setDataSource(path);
            Log.d("play:",path);
            //把视频画面输出到SurfaceView
            player.setDisplay(playView.getHolder());
            player.prepare();
            //播放
            player.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
