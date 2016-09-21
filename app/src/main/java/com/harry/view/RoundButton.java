/*
 * Copyright 2014-2015 ieclipse.cn.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.harry.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.harry.R;
import com.harry.util.ViewUtils;

/**
 * 类/接口描述
 * 
 * @author Jamling
 * @date 2015年11月19日
 *       
 */
public class RoundButton extends Button {
    
    /**
     * @param context
     */
    public RoundButton(Context context) {
        super(context);
        init(context, null);
    }
    
    /**
     * @param context
     * @param attrs
     */
    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    
    private RoundedColorDrawable mRoundBg;
    private int mRadius;
    private int borderWidth;
    private int borderColor;
    
    private void init(Context context, AttributeSet attrs) {
        Drawable bg = getBackground();
        if (bg == null){
            bg = new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent));
        }

        if (bg instanceof StateListDrawable) {
            // TODO support StateListDrawable
            // mRoundBg = new RoundedColorDrawable(radius, color);
//            StateListDrawable out = new StateListDrawable();
//            StateListDrawable sld = (StateListDrawable) bg;
//            int[] st = sld.getState();
//            for (int i = 0; i < st.length; i++) {
//                sld.selectDrawable(i);
//                sld.getCurrent();
//                
//            }
        }
        else if (bg instanceof ColorDrawable) {
            mRoundBg = RoundedColorDrawable
                    .fromColorDrawable((ColorDrawable) bg);
        }
        if (attrs != null) {
            int[] attr = R.styleable.RoundButton;
            TypedArray a = context.obtainStyledAttributes(attrs, attr);
            mRadius = a.getDimensionPixelOffset(
                    R.styleable.RoundButton_android_radius, mRadius);
            borderColor = a.getColor(R.styleable.RoundButton_borderColor,
                    Color.TRANSPARENT);
            borderWidth = a.getDimensionPixelOffset(
                    R.styleable.RoundButton_borderWidth, borderWidth);
            setRadius(mRadius);
            setBorder(borderColor, borderWidth);
//            if (a.hasValue(R.styleable.RoundButton_pressedBgColor)) {
//                setStateBgColor(new int[]{android.R.attr.state_pressed},
//                    a.getColor(R.styleable.RoundButton_pressedBgColor, Color.TRANSPARENT), borderColor);
//            }
//            if (a.hasValue(R.styleable.RoundButton_checkedBgColor)) {
//                setStateBgColor(new int[]{android.R.attr.state_checked},
//                    a.getColor(R.styleable.RoundButton_checkedBgColor, Color.TRANSPARENT), borderColor);
//            }
//            if (a.hasValue(R.styleable.RoundButton_selectedBgColor)) {
//                setStateBgColor(new int[]{android.R.attr.state_selected},
//                    a.getColor(R.styleable.RoundButton_selectedBgColor, Color.TRANSPARENT), borderColor);
//            }
            a.recycle();
        }
        
        ViewUtils.setBackground(this, mRoundBg);
    }
    
    public void setRadius(int radius) {
        if (mRoundBg != null) {
            mRoundBg.setRadius(radius);
        }
    }
    
    public void setBorder(int color, int width) {
        if (mRoundBg != null) {
            mRoundBg.setBorder(color, width);
        }
    }
    
    public void setRoundBackground(Drawable background) {
        if (background instanceof ColorDrawable) {
            mRoundBg = RoundedColorDrawable
                    .fromColorDrawable((ColorDrawable) background);
            mRoundBg.setRadius(mRadius);
            ViewUtils.setBackground(this, mRoundBg);
        }
    }
    
    public void setRoundBackgroundColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.setColor(color);
        }
        else {
            setRoundBackground(new ColorDrawable(color));
        }
    }

    public void addStateBgColor(int[] stateSet, int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(stateSet, color);
        }
    }

    public void setPressedBgColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(android.R.attr.state_pressed, color);
        }
    }

    public void setCheckedBgColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(android.R.attr.state_checked, color);
        }
    }

    public void setSelectedBgColor(int color) {
        if (mRoundBg != null) {
            mRoundBg.addStateColor(android.R.attr.state_selected, color);
        }
    }

    public void apply() {
        if (mRoundBg != null) {
            mRoundBg.applyTo(this);
        }
    }
}
