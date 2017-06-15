package com.harry.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/6/9
 */

@Module
public class BModule {
    
    @Provides
    BModel provideB(){
        return new BModel("bb");
    }
}
