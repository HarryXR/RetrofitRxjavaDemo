/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp.model;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/15.
 */
public class DownloadFunc implements Func1<ResponseBody, byte[]> {
    @Override
    public byte[] call(ResponseBody responseBody) {
        try {
            return responseBody.bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
