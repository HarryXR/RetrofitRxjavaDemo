/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.harry.progress.IndicatorActivity;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/12.
 */
public class ProgressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
    }

    public void startIndicator(View view){
        Intent intent=new Intent(this,IndicatorActivity.class);
        startActivity(intent);
    }

    public void startProgress(View view){
        Intent intent=new Intent(this,SweepGradientActivity.class);
        startActivity(intent);
    }
}
