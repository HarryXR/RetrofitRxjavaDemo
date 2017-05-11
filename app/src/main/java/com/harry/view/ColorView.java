package com.harry.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.harry.R;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/5/11.
 */

public class ColorView extends ImageView {
    
    private Paint myPaint = null;
    private Bitmap bitmap = null;
    private ColorMatrix myColorMatrix = null;
    private float[] colorArray = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
    
    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic1);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //新建画笔对象
        myPaint = new Paint();
        //描画（原始图片）
        canvas.drawBitmap(bitmap, 0, 0, myPaint);
        //新建颜色矩阵对象
        myColorMatrix = new ColorMatrix();
        //设置颜色矩阵的值
        myColorMatrix.set(colorArray);
        //myColorMatrix.setSaturation(0);//饱和度为0，黑白图片
        //设置画笔颜色过滤器
        myPaint.setColorFilter(new ColorMatrixColorFilter(myColorMatrix));
        //描画（处理后的图片）
        canvas.drawBitmap(bitmap, 0, 0, myPaint);
        invalidate();
    }
    
    //设置颜色数值
    public void setColorArray(float[] colorArray) {
        this.colorArray = colorArray;
    }
    
    //设置图片
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
