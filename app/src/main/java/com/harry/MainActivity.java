package com.harry.test;

import com.harry.test.design.DesignActivity;
import com.harry.test.layout.LayoutActivity;
import com.harry.test.ndk.TestNDK;
import com.harry.test.rxjava.RxJavaActivity;
import com.harry.test.rxjava.retrofit.TopActivity;
import com.harry.test.service.ServiceActivity;
import com.harry.test.view.TestActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected Class[] getActivities() {
        return new Class[]{ServiceActivity.class, LayoutActivity.class, TestNDK.class, TestActivity.class, RxJavaActivity.class,
            DesignActivity.class, TopActivity.class};
    }
}
