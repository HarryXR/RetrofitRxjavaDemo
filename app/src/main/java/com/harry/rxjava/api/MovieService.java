/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rxjava.api;

import com.harry.rxjava.model.BaseResponse;
import com.harry.rxjava.model.MovieResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

    @GET("in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie(@Query("start") int start);
}
