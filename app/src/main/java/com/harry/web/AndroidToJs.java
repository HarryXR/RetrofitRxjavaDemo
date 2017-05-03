package com.harry.web;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.Serializable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/5/3.
 */

public class AndroidToJs implements Serializable {
    
    Context context;
    public AndroidToJs(Context context){
        this.context=context;
    }
    @JavascriptInterface
    public void hello(String msg) {
        System.out.println("JS调用了Android的hello方法");
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
