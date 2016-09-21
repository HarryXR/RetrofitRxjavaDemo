/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.rxjava.retrofit;

import java.util.List;

import retrofit2.http.POST;
import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/19.
 */
public interface MovieService {
    @POST("in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie();

}
