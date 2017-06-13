package com.harry.dagger;

import android.util.Log;

import javax.inject.Inject;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/6/8
 */

public class AModel {
    @Inject
    BModel b;
    public AModel(){
        Log.e("AModel","AModel()");
    }
    
    public void log(){
        Log.e("AModel","log a");
    }
    
}
