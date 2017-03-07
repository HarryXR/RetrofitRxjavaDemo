/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日夜间模式切换
 *
 * @author Harry
 * @date 2017/3/7.
 */
public class DayNightActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_day)
    View mDay;
    @BindView(R.id.btn_night)
    View mNight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night);
        ButterKnife.bind(this);
        mDay.setOnClickListener(this);
        mNight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mDay) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
        else if (v == mNight) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        }
    }
}
