package com.harry.web;

import android.webkit.JavascriptInterface;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/5/3.
 */

public class AndroidToJs extends Object {
    @JavascriptInterface
    public void hello(String msg) {
        System.out.println("JS调用了Android的hello方法");
    }
}
