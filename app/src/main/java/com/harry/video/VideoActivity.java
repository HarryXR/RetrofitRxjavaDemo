/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.video;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.harry.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        mPLayer.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
            , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "嫂子闭眼睛");
        mPLayer.thumbImageView.setImageURI(Uri.parse("http://p.qpic"
            + ".cn/videoyun//02449_43b6f696980311e59ed467f22794e792_1/640"));
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
    }

}
