package com.harry.kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * 类/接口描述
 * @author Harry
 * @date 2017/5/18.
 */
class Kotlin1 @JvmOverloads constructor(context: Context, attrs:AttributeSet= null!!, def:Int=0 ) : View(context,attrs,
        def){
    var i=false;
    var j=i!!;
}