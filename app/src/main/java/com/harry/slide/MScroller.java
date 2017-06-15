package com.harry.slide;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/5/12.
 */

public class MScroller extends Scroller {
    
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };
    
    public boolean noDuration;
    
    public void setNoDuration(boolean noDuration) {
        this.noDuration = noDuration;
    }
    
    public MScroller(Context context) {
        this(context,sInterpolator);
    }
    
    public MScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        if(noDuration)
            //界面滑动不需要时间间隔
            super.startScroll(startX, startY, dx, dy, 0);
        else
            super.startScroll(startX, startY, dx, dy,duration);
    }
}
