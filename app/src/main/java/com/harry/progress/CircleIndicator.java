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

import com.harry.R;
import com.harry.util.ViewUtils;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/14.
 */
public class CircleIndicator extends BaseCircle {
    private static final int CIRCLE_STROKE_WIDTH = 16;
    int[] numbers = new int[]{5, 13, 20, 35, 60};
    int[] colors = new int[]{getResources().getColor(R.color.circle_red), getResources().getColor(
        R.color.circle_green), getResources().getColor(R.color.material_blue_grey_800), getResources().getColor(
        R.color.circle_gray)};

    private int CIRCLE_WHITE;

    public CircleIndicator(Context context) {
        this(context,null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CIRCLE_WHITE = getResources().getColor(R.color.white);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外面的数字
        drawOutsideText(canvas);
        //画进度
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(mOutIndicatorSize);
        paint.setColor(CIRCLE_WHITE);

        float perAnagle = (SWEEP_DEGREE / (mEndIndicator - mStartIndicator));
        float textHeight = ViewUtils.getTextHeight(paint);
        //环半径
        int radius = (int) (getViewRadius() - mDividerWidth * CIRCLE_GAP);
        float textRadius = radius - ViewUtils.getHeight(paint) / 2;

        RectF rectf = new RectF(getCenterX() - radius, getCenterY() - radius, getCenterX() + radius,
            getCenterY() + radius);
        Path path = new Path();
        path.addCircle(getCenterX(), getCenterY(), textRadius, Path.Direction.CW);

        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(CIRCLE_STROKE_WIDTH + textHeight);
        circlePaint.setStyle(Paint.Style.STROKE);

        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setColor(colors[0]);
        drawCircleContent(canvas, paint, circlePaint, path, rectf, perAnagle, textRadius, 5, 13,"过低");
        circlePaint.setColor(colors[3]);//先画最后一个，然后倒数第二个将最后一个圆角覆盖
        drawCircleContent(canvas, paint, circlePaint, path, rectf, perAnagle, textRadius, 35, 60,"超级高");
        circlePaint.setStrokeCap(Paint.Cap.BUTT);
        circlePaint.setColor(colors[1]);
        drawCircleContent(canvas, paint, circlePaint, path, rectf, perAnagle, textRadius, 13, 20,"正常");
        circlePaint.setColor(colors[2]);
        drawCircleContent(canvas, paint, circlePaint, path, rectf, perAnagle, textRadius, 20, 35,"过高");
    }

    private void drawCircleContent(Canvas canvas, Paint paint, Paint circlePaint, Path path, RectF rectf,
                                   float perAnagle, float textRadius, int start, int end,String state) {
        float startAngle = START_DEGREE;
        startAngle += perAnagle * (start - mStartIndicator);
        float endAngle = perAnagle * (end - start);
        //画进度
        canvas.drawArc(rectf, startAngle, endAngle, false, circlePaint);
        //画文字
        float circlePathLength=ViewUtils.getCirclePathLength(textRadius,(startAngle+endAngle/2));
        circlePathLength-=ViewUtils.getTextWidth(paint,state)/2;
        canvas.drawTextOnPath(state, path, circlePathLength, 0, paint);
    }

    private void drawOutsideText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mNormalGray);
        paint.setTextSize(mOutIndicatorSize);

        int radius = getViewRadius() - mDividerWidth;
        Path path = new Path();
        path.addCircle(getCenterX(), getCenterY(), radius, Path.Direction.CW);
        //第一个数字
        int number = 5;
        float hOffset = ViewUtils.getCirclePathLength(radius, START_DEGREE);//通过弧长定位初始位置
        hOffset -= ViewUtils.getTextWidth(paint, number + "") / 2;
        canvas.drawTextOnPath(number + "", path, hOffset, 0, paint);
        float perAngle = SWEEP_DEGREE / (mEndIndicator - mStartIndicator);
        for (int i = 1; i < 5; i++) {
            number = numbers[i];
            hOffset = ViewUtils.getCirclePathLength(radius, (START_DEGREE + perAngle * (number - mStartIndicator)));
            hOffset -= ViewUtils.getTextWidth(paint, number + "") / 2;
            canvas.drawTextOnPath(number + "", path, hOffset, 0, paint);
        }
    }
}
