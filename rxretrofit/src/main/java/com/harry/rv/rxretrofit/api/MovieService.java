/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.rxretrofit.api;

import com.harry.rv.rxretrofit.model.BaseResponse;
import com.harry.rv.rxretrofit.model.MovieResponse;
import com.harry.rv.rxretrofit.model.PostResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 类/接口描述
 *
 * @author Harry
 */
public interface MovieService {
    @POST("in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie();

    @GET("in_theaters")
    Observable<BaseResponse<List<MovieResponse>>> getMovie(@Query("start") int start);

    @Multipart
    @POST("face")
    Observable<BaseResponse<PostResponse>> upload(@Part("file\"; filename=\"microMsg.1460895294032.jpg\"") RequestBody
                                                  imgs);

    @POST("face")
    Observable<BaseResponse<PostResponse>> upload(@Body MultipartBody
                                                      imgs);
}
