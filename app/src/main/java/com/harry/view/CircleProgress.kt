<<<<<<< HEAD
/*
 * Created by ttdevs at 16-9-20 下午5:34.
 * E-mail:ttdevs@gmail.com
 * https://github.com/ttdevs
 * Copyright (c) 2016 ttdevs
 */

package com.harry.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CircleProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val CIRCLE_STROKE_COLOR = Color.parseColor("#FFFFB182")
    private val CIRCLE_STROKE_COLOR_END = Color.parseColor("#FFFF7C4D")

    private var mSize = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specSize = View.MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(specSize, specSize) // TODO: 16/9/20 参考宽，处理成正方形
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mSize = w
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val oval = RectF(
                CIRCLE_STROKE_WIDTH.toFloat(),
                CIRCLE_STROKE_WIDTH.toFloat(),
                (mSize - CIRCLE_STROKE_WIDTH).toFloat(),
                (mSize - CIRCLE_STROKE_WIDTH).toFloat())

        val circlePaint = Paint()
        circlePaint.isAntiAlias = true
        circlePaint.strokeWidth = CIRCLE_STROKE_WIDTH.toFloat()
        circlePaint.style = Paint.Style.STROKE
        //        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        val matrix = Matrix()
        matrix.preRotate(CIRCLE_START_ANGLE.toFloat(), (mSize / 2).toFloat(), (mSize / 2).toFloat())
        val gradient = SweepGradient((mSize / 2).toFloat(), (mSize / 2).toFloat(),
                CIRCLE_STROKE_COLOR, CIRCLE_STROKE_COLOR_END)
        gradient.setLocalMatrix(matrix)
        circlePaint.shader = gradient
        canvas.drawArc(oval, CIRCLE_START_ANGLE.toFloat(), progress.toFloat(), false, circlePaint)

        val fullPaint = Paint()
        fullPaint.isAntiAlias = true
        circlePaint.style = Paint.Style.STROKE
        fullPaint.strokeCap = Paint.Cap.ROUND
        fullPaint.color = Color.TRANSPARENT

        if (progress == 360) {
            fullPaint.color = CIRCLE_STROKE_COLOR_END
        } else if (progress > 1) {
            fullPaint.color = CIRCLE_STROKE_COLOR
        }
        //        canvas.drawCircle(mSize / 2, CIRCLE_STROKE_WIDTH, CIRCLE_STROKE_WIDTH / 2, fullPaint);

    }

    var progress = 0
        private set

    /**
     * 设置进度

     * @param progress
     */
    fun setProgress(progress: Float) {
        var progress = progress
        if (progress < 0) {
            progress = 0f
        }
        if (progress > 1) {
            progress = 1.0f
        }
        this.progress = (progress * 360).toInt()
        invalidate()
    }

    companion object {
        private val CIRCLE_START_ANGLE = -90 // 指示圆环开始角度
        private val CIRCLE_STROKE_WIDTH = 16 // 指示圆环的宽度
    }
=======
/*
 * Created by ttdevs at 16-9-20 下午5:34.
 * E-mail:ttdevs@gmail.com
 * https://github.com/ttdevs
 * Copyright (c) 2016 ttdevs
 */

package com.harry.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CircleProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val CIRCLE_STROKE_COLOR = Color.parseColor("#FFFFB182")
    private val CIRCLE_STROKE_COLOR_END = Color.parseColor("#FFFF7C4D")

    private var mSize = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specSize = View.MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(specSize, specSize) // TODO: 16/9/20 参考宽，处理成正方形
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mSize = w
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val oval = RectF(
                CIRCLE_STROKE_WIDTH.toFloat(),
                CIRCLE_STROKE_WIDTH.toFloat(),
                (mSize - CIRCLE_STROKE_WIDTH).toFloat(),
                (mSize - CIRCLE_STROKE_WIDTH).toFloat())

        val circlePaint = Paint()
        circlePaint.isAntiAlias = true
        circlePaint.strokeWidth = CIRCLE_STROKE_WIDTH.toFloat()
        circlePaint.style = Paint.Style.STROKE
        //        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        val matrix = Matrix()
        matrix.preRotate(CIRCLE_START_ANGLE.toFloat(), (mSize / 2).toFloat(), (mSize / 2).toFloat())
        val gradient = SweepGradient((mSize / 2).toFloat(), (mSize / 2).toFloat(),
                CIRCLE_STROKE_COLOR, CIRCLE_STROKE_COLOR_END)
        gradient.setLocalMatrix(matrix)
        circlePaint.shader = gradient
        canvas.drawArc(oval, CIRCLE_START_ANGLE.toFloat(), progress.toFloat(), false, circlePaint)

        val fullPaint = Paint()
        fullPaint.isAntiAlias = true
        circlePaint.style = Paint.Style.STROKE
        fullPaint.strokeCap = Paint.Cap.ROUND
        fullPaint.color = Color.TRANSPARENT

        if (progress == 360) {
            fullPaint.color = CIRCLE_STROKE_COLOR_END
        } else if (progress > 1) {
            fullPaint.color = CIRCLE_STROKE_COLOR
        }
        //        canvas.drawCircle(mSize / 2, CIRCLE_STROKE_WIDTH, CIRCLE_STROKE_WIDTH / 2, fullPaint);

    }

    var progress = 0
        private set

    /**
     * 设置进度

     * @param progress
     */
    fun setProgress(progress: Float) {
        var progress = progress
        if (progress < 0) {
            progress = 0f
        }
        if (progress > 1) {
            progress = 1.0f
        }
        this.progress = (progress * 360).toInt()
        invalidate()
    }

    companion object {
        private val CIRCLE_START_ANGLE = -90 // 指示圆环开始角度
        private val CIRCLE_STROKE_WIDTH = 16 // 指示圆环的宽度
    }
>>>>>>> b21739f8016bf954db46b47da3b0383cfd98620a
}