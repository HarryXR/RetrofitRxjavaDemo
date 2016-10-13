/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.progress;

import android.app.Activity;
import android.os.Bundle;

import com.harry.R;
import com.harry.view.LineIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/12.
 */
public class ProgressActivity extends Activity {
    @BindView(R.id.li)
    LineIndicator lineIndicator;
//    ProgressBar p=new ProgressBar(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);
//        p.setProgress(1);
        String leftAlert = "开始";
        String leftContent = "60.0公斤";
        String rightAlert = "目标";
        String rightContent = "50.0公斤";
        lineIndicator.setContent(leftAlert, leftContent, rightAlert, rightContent);
//        for(int i=0;i<10;i++){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            lineIndicator.setProgress(1/10f);
        try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        lineIndicator.setProgress(2/10f);
    }
}
