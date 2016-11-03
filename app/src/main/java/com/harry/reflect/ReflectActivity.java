/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.reflect;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.harry.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/31.
 */
public class ReflectActivity extends Activity {
    @BindView(R.id.tv_title)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        ButterKnife.bind(this);
        MyDate m = new MyDate();
        Class cls = m.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fields.length && fields.length > 0; i++) {
            fields[i].setAccessible(true);
            Object val;
            try {
                val = fields[i].get(m);
            } catch (IllegalAccessException e) {
                continue;
            }
            try {
                fields[i].set(m,30);
                val=fields[i].get(m);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sb.append(fields[i].getName() + "=" + val);
        }

        Method[] methods=cls.getMethods();
        for (int i=0;i<methods.length;i++){
            sb.append("\n");
            Annotation[] anns=methods[i].getAnnotations();
            for(int j=0;j<anns.length;j++){
                sb.append(methods[i].getName()+"=@="+anns[j]);
            }

        }
        tv.setText(sb);
    }
}
