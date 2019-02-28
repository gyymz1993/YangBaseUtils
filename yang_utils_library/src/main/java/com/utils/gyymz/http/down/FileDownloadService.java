package com.utils.gyymz.http.down;

import android.annotation.SuppressLint;

import com.google.gson.GsonBuilder;
import com.utils.gyymz.http.IApiService;
import com.utils.gyymz.http.callback.DownloadSubscriber;
import com.utils.gyymz.http.subscriber.ApiFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 Yang
 * @创建时间 2018/12/28 10:39
 */

public class FileDownloadService {
    private static FileDownloadService mInstance = null;
    private static final int DEFAULT_TIMEOUT = 30; //默认超时时间30秒
    private final IApiService mApiService;

    private FileDownloadService() {
        OkHttpClient OkhttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofitClient = new Retrofit.Builder()
                .client(OkhttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())) //转换器，请求结果转换成VO
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //配合Rxjava使用，将retofit的call映射成Observable
                .baseUrl(ApiFactory.getFactory().baseUrl)
                .build();
        mApiService = retrofitClient.create(IApiService.class);


    }

    public static FileDownloadService getInstance() {
        if (null == mInstance) {
            synchronized (FileDownloadService.class) {
                if (null == mInstance) {
                    mInstance = new FileDownloadService();
                }
            }
        }
        return mInstance;
    }

    /**
     * 文件下载
     * subscribeOn()改变调用它之前代码的线程
     * observeOn()改变调用它之后代码的线程
     */
    @SuppressLint("CheckResult")
    public void download(String url, DownloadSubscriber downloadSubscriber) {
        mApiService.download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //.observeOn(Schedulers.computation()) // 用于计算任务
                .subscribeWith(downloadSubscriber);
    }

}
