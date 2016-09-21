/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.rxjava.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.harry.test.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/19.
 */
public class TopActivity extends Activity implements MovieController.LoadListener {

    @BindView(R.id.lv)
    TextView mLv;

    MovieController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        mController = new MovieController(this);
        load();
    }

    private void load() {
        mController.load();
    }

    @Override
    public void onSuccess(List<MovieResponse> out) {
        StringBuilder sb = new StringBuilder();
        for (MovieResponse res : out) {
            sb.append(res.title + "\n");
        }
        mLv.setText(sb.toString());
    }

    @Override
    public void onError(Throwable error) {
        Log.e("TopActivity", error.getMessage());
    }

    @Override
    public void onComplete() {
        Log.e("TopActivity", "onCompleted");
    }
}
