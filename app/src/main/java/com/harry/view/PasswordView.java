package com.harry.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.harry.util.AppUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/5/17.
 */

public class PasswordView extends View {
    private int borderWidth;
    private int borderColor;
    private int cursorPosition;
    private int cursorWidth;
    private int cursorHeight;
    private int passwordSize;
    private int passwordPadding;//密码间隔
    private int passwordLength;//密码位数
    private int textSize;
    private String CIPHER_TEXT = "*";
    
    private String[] password;
    PasswordListener passwordListener;
    InputMethodManager inputManager;
    private boolean isInputComplete = false;
    private boolean isCursorShowing = true;
    
    private Timer timer;
    private TimerTask timerTask;
    
    public PasswordView(Context context) {
        this(context, null);
    }
    
    public PasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    Paint paint;
    
    private void init(Context context) {
        passwordSize = AppUtils.dp2px(context, 40);
        cursorWidth = 4;
        passwordLength = 4;
        textSize = 24;
        passwordPadding = AppUtils.dp2px(context, 20);
        password = new String[passwordLength];
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(borderWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);
        
        setFocusableInTouchMode(true);
        MyKeyListener MyKeyListener = new MyKeyListener();
        setOnKeyListener(MyKeyListener);
        inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        
        timerTask = new TimerTask() {
            @Override
            public void run() {
                isCursorShowing = !isCursorShowing;
                postInvalidate();
            }
        };
        timer = new Timer();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                width = passwordSize * passwordLength + passwordPadding * (passwordLength - 1);
                break;
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                //TODO mesure password size
                break;
        }
        setMeasuredDimension(width, heightMeasureSpec);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textSize = passwordSize / 2;//密码框的一半
        cursorHeight = passwordSize / 2;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawUnderLine(canvas);
        drawText(canvas);
        drawCursor(canvas);
    }
    
    private void drawCursor(Canvas canvas) {
        if (!isCursorShowing & !isInputComplete && hasFocus()) {
            canvas.drawLine((getPaddingLeft() + passwordSize / 2) + (passwordSize + passwordPadding) * cursorPosition,
                getPaddingTop() + (passwordSize - cursorHeight) / 2,
                (getPaddingLeft() + passwordSize / 2) + (passwordSize + passwordPadding) * cursorPosition,
                getPaddingTop() + (passwordSize + cursorHeight) / 2, paint);
        }
    }
    
    //画*
    private void drawText(Canvas canvas) {
        paint.setColor(Color.GRAY);
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        canvas.getClipBounds(rect);
        paint.getTextBounds(CIPHER_TEXT, 0, CIPHER_TEXT.length(), rect);
        int cHeight = rect.height();
        float y = cHeight / 2f + rect.height() / 2f - rect.bottom;
        for (int i = 0; i < password.length; i++) {
            if (!TextUtils.isEmpty(password[i])) {
                canvas.drawText(CIPHER_TEXT,
                    getPaddingLeft() + passwordSize / 2 + (passwordSize + passwordPadding) * i - rect.right / 2,
                    getPaddingTop() + y + passwordSize / 2, paint);
            }
        }
    }
    
    private void drawUnderLine(Canvas canvas) {
        for (int i = 0; i < passwordLength; i++) {
            canvas.drawLine(getPaddingLeft() + (passwordSize + passwordPadding) * i, getPaddingTop() + passwordSize,
                getPaddingLeft() + (passwordSize + passwordPadding) * i + passwordSize, getPaddingTop() + passwordSize,
                paint);
        }
    }
    
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;
        return super.onCreateInputConnection(outAttrs);
    }
    
    /**
     * 密码监听者
     */
    public interface PasswordListener {
        /**
         * 输入/删除监听
         *
         * @param changeText 输入/删除的字符
         */
        void passwordChange(String changeText);
        
        /**
         * 输入完成
         */
        void passwordComplete();
        
        /**
         * 确认键后的回调
         *
         * @param password   密码
         * @param isComplete 是否达到要求位数
         */
        void keyEnterPress(String password, boolean isComplete);
    }
    
    class MyKeyListener implements OnKeyListener {
        
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            int action = event.getAction();
            if (action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    /**
                     * 删除操作
                     */
                    if (TextUtils.isEmpty(password[0])) {
                        return true;
                    }
                    String deleteText = delete();
                    if (passwordListener != null && !TextUtils.isEmpty(deleteText)) {
                        passwordListener.passwordChange(deleteText);
                    }
                    postInvalidate();
                    return true;
                }
                if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                    /**
                     * 只支持数字
                     */
                    if (isInputComplete) {
                        return true;
                    }
                    String addText = add((keyCode - 7) + "");
                    if (passwordListener != null && !TextUtils.isEmpty(addText)) {
                        passwordListener.passwordChange(addText);
                    }
                    postInvalidate();
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    /**
                     * 确认键
                     */
                    if (passwordListener != null) {
                        passwordListener.keyEnterPress(getPassword(), true);
                    }
                    return true;
                }
            }
            return false;
        }
    }
    
    private String getPassword() {
        StringBuilder pwd = new StringBuilder("");
        for (int i = 0; i < password.length; i++) {
            pwd.append(password[i]);
        }
        return pwd.toString();
    }
    
    /**
     * 删除
     */
    private String delete() {
        String deleteText = null;
        if (cursorPosition > 0) {
            deleteText = password[cursorPosition - 1];
            password[cursorPosition - 1] = null;
            cursorPosition--;
        }
        else if (cursorPosition == 0) {
            deleteText = password[cursorPosition];
            password[cursorPosition] = null;
        }
        isInputComplete = false;
        return deleteText;
    }
    
    /**
     * 增加
     */
    private String add(String c) {
        String addText = null;
        if (cursorPosition < passwordLength) {
            addText = c;
            password[cursorPosition] = c;
            cursorPosition++;
            if (cursorPosition == passwordLength) {
                isInputComplete = true;
                if (passwordListener != null) {
                    passwordListener.passwordComplete();
                }
            }
        }
        return addText;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /**
             * 弹出软键盘
             */
            requestFocus();
            inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            return true;
        }
        return super.onTouchEvent(event);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
        super.onWindowFocusChanged(hasWindowFocus);
       
    }
    
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timer.scheduleAtFixedRate(timerTask, 0, 500);
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }
}
