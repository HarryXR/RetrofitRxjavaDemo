/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.rxretrofit.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        HttpUrl url=original.url().newBuilder()
            .addQueryParameter("client", "Android")
//        .addQueryParameter("uid", 52+"")
//        .addQueryParameter("token", "e54517e6605e224af68b803a0cc8eb63")
        .addQueryParameter("uuid", "-1456468860997233324")
            .build();

        Request request = original.newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Connection", "keep-alive")
            .method(original.method(), original.body())
            .url(url)
            .build();

        return chain.proceed(request);
    }
}
