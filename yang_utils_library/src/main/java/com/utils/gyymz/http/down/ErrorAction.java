package com.utils.gyymz.http.down;


import android.text.TextUtils;

import com.utils.gyymz.utils.L_;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


public class ErrorAction implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {
        L_.i("异常日志", throwable);
        if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            L_.i("网络错误");
        } else if (throwable instanceof SocketTimeoutException) {
            L_.i("连接超时，请重试");
        } else if (throwable instanceof HttpException) {
            L_.i("服务器错误(" + ((HttpException) throwable).code());
        } else {
            //onApiError(throwable);
            //未知错误，最好将其上报给服务端，供异常排查
            if (!TextUtils.isEmpty(throwable.getMessage())) {
                L_.i(throwable.getMessage());
            }
        }
    }

    public void onApiError(Exception throwable) {
        //有errorMsg优先吐msg,没有吐errcode,两者区别：msg一般是比较友好的中文说明
        if (throwable.getMessage() != null)
            L_.i(throwable.getMessage());
        else if (throwable.getMessage() != null) {
            L_.i(throwable.getMessage());
        }
    }

}
