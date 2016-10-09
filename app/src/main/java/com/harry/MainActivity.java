package com.harry;

import com.harry.rxjava.RxJavaActivity;
import com.harry.rxjava.UploadActivity;
import com.harry.rxjava.retrofit.TopActivity;
import com.harry.video.MovieRecorderActivity;
import com.harry.video.VideoActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected Class[] getActivities() {
        return new Class[]{RxJavaActivity.class, TopActivity.class, UploadActivity.class, VideoActivity.class,
            MovieRecorderActivity.class};
    }
}
