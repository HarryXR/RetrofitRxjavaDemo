package com.harry.dagger;

import android.util.Log;

import javax.inject.Inject;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/6/8
 */

public class BModel {
    
//    @Inject
//    public BModel(){
//        Log.e("BModel","BModel()");
//    }
    
    @Inject
    public BModel(String s){
        Log.e("BModel","BModel()"+s);
    }
    
   
}
