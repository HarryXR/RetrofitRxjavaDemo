package com.harry.dagger;

import dagger.Component;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/6/7
 */

@Component(modules = {BModule.class})
public interface ModelComponent {
    
    void inject(AModel a);
}
