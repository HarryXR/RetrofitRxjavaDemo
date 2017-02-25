/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.service.binder;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.harry.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/24.
 */
public class BinderActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.btn_speak)
    Button speak;
    @BindView(R.id.btn_cal)
    Button cal;

    private BinderPool mBinderPool;
    private ISpeak mSpeak;
    private ICalculate mCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        ButterKnife.bind(this);
        speak.setOnClickListener(this);
        cal.setOnClickListener(this);
        getBinderPool();
    }

    private void getBinderPool() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBinderPool = BinderPool.getInstance(BinderActivity.this);
            }
        }) {

        }.start();
    }

    @Override
    public void onClick(View v) {
        if (v == speak) {
            mSpeak = Speak.asInterface(mBinderPool.queryBinder(1));//(Speak)mBinderPool.queryBinder(1)
            try {
                Toast.makeText(this, mSpeak.speak(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (v == cal) {
            mCalculate = Calculate.asInterface(mBinderPool.queryBinder(2));
            try {
                Toast.makeText(this, "result=" + mCalculate.add(2, 4), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
