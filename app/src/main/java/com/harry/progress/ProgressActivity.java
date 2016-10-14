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
    @BindView(R.id.ci)
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);
        String leftAlert = "开始";
        String leftContent = "60.0公斤";
        String rightAlert = "目标";
        String rightContent = "50.0公斤";
        lineIndicator.setContent(leftAlert, leftContent, rightAlert, rightContent);
        lineIndicator.setIndicator(60, 50, 55);

        circleIndicator.mTitle="体脂率";
    }
}
