/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.harry.R;
import com.harry.util.ViewUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/14.
 */
public class BaseCircle extends View {
    protected static final int START_DEGREE = 135;
    protected static final int SWEEP_DEGREE = 270;
    protected static final float CIRCLE_GAP = 2.6f;

    protected int mDividerWidth = 32;

    protected int mNormalGray;

    protected int mAlertColor, mTitleColor, mContentColor, mUnitColor;
    protected int mCircleBackground, mCircleGreen, mCircleGray;
    protected int mIndicatorCenter, mIndicatorGray, mIndicatorLight;
    protected int mCenterCircleOut, mCenterCircleMiddle, mCenterCircleInside;
    protected int mOutIndicatorSize, mAlertSize, mContentSize, mUnitSize, mTitleSize;
    protected int mWidth, mHeight;

    public BaseCircle(Context context) {
        this(context, null);
    }

    public BaseCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mCircleBackground = getResources().getColor(R.color.circle_background);
        mCircleGray = getResources().getColor(R.color.circle_gray);
        mCircleGreen = getResources().getColor(R.color.circle_green);

        mDividerWidth = (int) getResources().getDimension(R.dimen.divider_width);
        mOutIndicatorSize = (int) getResources().getDimension(R.dimen.out_indicator_size);
        mTitleSize = (int) getResources().getDimension(R.dimen.title_indicator_size);
        mContentSize = (int) getResources().getDimension(R.dimen.content_indicator_size);
        mUnitSize = (int) getResources().getDimension(R.dimen.unit_indicator_size);
        mAlertSize = (int) getResources().getDimension(R.dimen.alert_indicator_size);

        mCenterCircleOut = (int) getResources().getDimension(R.dimen.center_circle_out);
        mCenterCircleMiddle = (int) getResources().getDimension(R.dimen.center_circle_middle);
        mCenterCircleInside = (int) getResources().getDimension(R.dimen.center_circle_inside);

        mNormalGray = getResources().getColor(R.color.normal_gray);

        mIndicatorCenter = getResources().getColor(R.color.indicator_center);
        mIndicatorGray = getResources().getColor(R.color.indicator_gray);
        mIndicatorLight = getResources().getColor(R.color.indicator_light);

        mTitleColor = getResources().getColor(R.color.circle_title);
        mAlertColor = getResources().getColor(R.color.circle_alert);
        mContentColor = getResources().getColor(R.color.circle_content);
        mUnitColor = getResources().getColor(R.color.circle_unit);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画底部白色大背景，大圆
        drawBackground(canvas);
        //画中间内容
        drawContent(canvas);
        //画指针
        drawIndicator(canvas);
    }

    protected float mStartIndicator = 5f, mEndIndicator = 60f, mIndicator = 10f;

    protected String mTitle = "体脂率", mContent = "23", mUnit = "%", mAlert = "体脂过高 ";

    private void drawContent(Canvas canvas) {
        int radius = (int) (getViewRadius() - mDividerWidth * CIRCLE_GAP);
        //title
        Paint title = new Paint();
        title.setAntiAlias(true);
        title.setTextSize(mTitleSize);
        title.setColor(mTitleColor);
        int titleX = getCenterX() - ViewUtils.getTextWidth(title, mTitle) / 2;
        int totleY = getCenterY() - radius * 3 / 5;
        canvas.drawText(mTitle, titleX, totleY, title);
        //line
        Paint line = new Paint();
        line.setColor(mCircleGray);
        line.setStrokeWidth(3);
        int startX = getCenterX() - (int) (radius * Math.sin(Math.PI / 4)) + mDividerWidth;
        int stopX = getCenterX() + (int) (radius * Math.sin(Math.PI / 4)) - mDividerWidth;
        int startY = getCenterY() + (int) (radius * Math.sin(Math.PI / 4));
        startY += mDividerWidth / 2;//矫正
        canvas.drawLine(startX, startY, stopX, startY, line);
        //content 23
        Paint content = new Paint();
        content.setAntiAlias(true);
        content.setColor(mContentColor);
        content.setTextSize(mContentSize);
        //unit %
        Paint unit = new Paint();
        unit.setAntiAlias(true);
        unit.setColor(mUnitColor);
        unit.setTextSize(mUnitSize);
        int unitWith = ViewUtils.getTextWidth(unit, mUnit);

        int contentWidth = ViewUtils.getTextWidth(content, mContent);
        int contentX = getCenterX() - (contentWidth + unitWith) / 2;//保证 23%连续显示
        int contentY = startY - mDividerWidth / 2;
        canvas.drawText(mContent, contentX, contentY, content);

        int unitX = contentX + contentWidth;
        int unitY = startY - mDividerWidth / 2;
        canvas.drawText(mUnit, unitX, unitY, unit);
        //alert
        Paint alert = new Paint();
        alert.setAntiAlias(true);
        alert.setTextSize(mAlertSize);
        alert.setColor(mAlertColor);
        int alertX = getCenterX() - ViewUtils.getTextWidth(alert, mAlert) / 2;
        int alertY = startY + mDividerWidth * 3 / 2;
        canvas.drawText(mAlert, alertX, alertY, alert);
    }

    private void drawBackground(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setColor(mCircleBackground);
        int radius = getViewRadius() - mDividerWidth / 2 * 3;

        canvas.drawCircle(getCenterX(), getCenterY(), radius, bgPaint);

        Path path = new Path();
        path.addCircle(getCenterX(), getCenterY(), radius, Path.Direction.CW);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mCircleGray);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }

    protected void drawIndicator(Canvas canvas) {
        //画大圆
        Paint center = new Paint();
        center.setColor(mIndicatorCenter);
        canvas.drawCircle(getCenterX(),getCenterY(),mCenterCircleOut,center);

        //画两个1/4小圆，组成半圆
        float angle=SWEEP_DEGREE*(mIndicator-mStartIndicator)/(mEndIndicator-mStartIndicator);

        RectF rectf=new RectF(getCenterX()-mCenterCircleMiddle,getCenterY()-mCenterCircleMiddle,getCenterX()
            +mCenterCircleMiddle,getCenterY()+mCenterCircleMiddle);
        Paint light=new Paint();
        light.setColor(mIndicatorLight);
        canvas.drawArc(rectf,START_DEGREE+angle+90,90,true,light);
        Paint dark=new Paint();
        dark.setColor(mIndicatorGray);
        canvas.drawArc(rectf,START_DEGREE+angle+180,90,true,dark);

        drawArrow(canvas,angle,light,true);
        drawArrow(canvas,angle,dark,false);

        //画小圆
        canvas.drawCircle(getCenterX(),getCenterY(),mCenterCircleInside,center);
    }

    private void drawArrow(Canvas canvas, float angle, Paint dark, boolean b) {
        double oneAngle=(START_DEGREE+angle-90)*Math.PI/180;
        if(b){
            oneAngle=(START_DEGREE+angle+90)*Math.PI/180;
        }
        int oneX=(int)(mCenterCircleMiddle*Math.cos(oneAngle));
        oneX=getCenterX()+oneX;
        int oneY=(int)(mCenterCircleMiddle*Math.sin(oneAngle));
        oneY=getCenterY()+oneY;

        double twoAngle=(START_DEGREE+angle)*Math.PI/180;
        int middleRadius= (int) (getViewRadius()-CIRCLE_GAP*mDividerWidth);
        int twoX=(int)(middleRadius*Math.cos(twoAngle));
        twoX=getCenterX()+twoX;
        int twoY=(int)(middleRadius*Math.sin(twoAngle));
        twoY=getCenterY()+twoY;

        Path path=new Path();
        path.moveTo(getCenterX(),getCenterY());
        path.lineTo(oneX,oneY);
        path.lineTo(twoX,twoY);
        path.close();
        canvas.drawPath(path,dark);
    }

    public int getViewRadius() {

        return getCenterY() > getCenterX() ? getCenterX() : getCenterY();
    }

    protected int getCenterX() {
        return mWidth / 2;
    }

    protected int getCenterY() {
        return mHeight / 2;
    }
}
