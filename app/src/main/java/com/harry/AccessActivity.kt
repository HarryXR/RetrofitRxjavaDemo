package com.harry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import java.lang.reflect.ParameterizedType

/**
 * 类/接口描述
 * @author Harry
 * @date 2016/12/22.
 */
class AccessActivity<Output>(l: AccessActivity.SimpleListener<Output>) : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access)

        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)

        if(savedInstanceState is Bundle){

        }
        var b=savedInstanceState as Bundle
    }

    private var clazz: Class<Output>? = null

    init {

        val type = l.javaClass.genericInterfaces[0] as ParameterizedType
        val atype = type.actualTypeArguments[0]
        if (atype is Class<*>) {
            clazz = atype as Class<Output>
        } else if (atype is ParameterizedType) {
            clazz = atype.actualTypeArguments[0] as Class<Output>
        }
    }

fun getClazz():Class<Output>{
    return clazz as Class<Output>
}
    interface SimpleListener<Output> {
        fun onSuccess(output: Output)

        fun onError(error: String)
    }


}
