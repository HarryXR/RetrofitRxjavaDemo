package com.harry;

import com.harry.rxjava.RxJavaActivity;
import com.harry.rxjava.retrofit.TopActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected Class[] getActivities() {
        return new Class[]{RxJavaActivity.class, TopActivity.class};
    }
}
