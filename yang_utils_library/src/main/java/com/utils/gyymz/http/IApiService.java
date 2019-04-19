package com.utils.gyymz.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface IApiService {

    @Streaming  //防止OOM最好加上这个注解
    @GET
    Observable<ResponseBody> download(@Url String url);
}
