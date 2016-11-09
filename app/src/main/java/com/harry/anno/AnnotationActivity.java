/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.anno;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.harry.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/2.
 */
public class AnnotationActivity extends Activity {
    @BindView(R.id.tv)
    TextView tv;

    @AnnotationSingle(getName = "annotation")
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ButterKnife.bind(this);
       for(Field field:this.getClass().getDeclaredFields()) {
           if (field.getType() == String.class && field.getAnnotation(AnnotationSingle.class) != null) {
               tv.setText(field.getAnnotation(AnnotationSingle.class).getName());
           }
       }
    }
}
