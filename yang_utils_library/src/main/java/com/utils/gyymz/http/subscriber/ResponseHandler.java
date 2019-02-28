package com.utils.gyymz.http.subscriber;

import com.utils.gyymz.base.mvp.BaseView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.HttpException;

/**
* @作者    Yang
* @创建时间 2018/12/7 10:02
 *
 * 网络结果处理类, 此类会判断网络错误与业务错误.
*
*/

public class ResponseHandler<T> {

    private CustomHandler<T> handler;
    private BaseView mView;

    public ResponseHandler(CustomHandler<T> handler, BaseView view) {
        this.handler = handler;
        this.mView = view;
    }


    public boolean checkDataNotNull(IBaseData data) {
        return data != null && data.data() != null;
    }

    public boolean checkListNotNull(List data) {
        return data != null && data.size() > 0;
    }

    public void onComplete() {
        release();
    }

    public void onError(Throwable e) {
        e.printStackTrace();
        if (!handler.error(e)) {
            handleException(e);
        }
        if (mView!=null){
            mView.showNetWorkErrorView();
        }
        release();
    }

    public void onNext(T t) {
        IBaseData data;
        if (t instanceof IBaseData) {
            data = (IBaseData) t;
            if (data.code() == 1) {
                handler.success(t);
            } else {
                if (!handler.operationError(t, data.code(), data.msg())) {
                    handleOperationError(data.msg());
                }
            }
        } else {
            handler.success(t);
        }
        if (mView!=null){
            mView.hideLoading();
        }
        release();
    }


    public void release() {
        handler = null;
    }

    public void handleException(Throwable e) {
        if (e instanceof ConnectException) {
        } else if (e instanceof HttpException) {
        } else if (e instanceof SocketTimeoutException) {
        } else {
        }
    }

    public void handleOperationError(String message) {
        handler.requestError(message);
    }


    public void requestError(String message) {
        handler.requestError(message);
    }


    public interface CustomHandler<T> {

        /**
         * 请求成功同时业务成功的情况下会调用此函数
         */
        void success(T t);


        /**
         * 请求成功同时业务成功的情况下会调用此函数
         */
        void requestError(String exception);


        /**
         * 请求成功但业务失败的情况下会调用此函数.
         *
         * @return 是否需要自行处理业务错误.
         * <p>
         * true - 需要, 父类不会处理错误
         * </P>
         * false - 不需要, 交由父类处理
         */
        boolean operationError(T t, int status, String message);

        /**
         * 请求失败的情况下会调用此函数
         *
         * @return 是否需要自行处理系统错误.
         * <p>
         * true - 需要, 父类不会处理错误
         * </P>
         * false - 不需要, 交由父类处理
         */
        boolean error(Throwable e);


    }

    public interface IBaseData {

        int code();

        Object data();

        String msg();
    }
}
