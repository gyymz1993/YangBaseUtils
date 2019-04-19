package com.utils.gyymz.base.mvp;

import android.support.annotation.NonNull;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
* @作者    Yang
* @创建时间 2018/12/27 14:48
*
*/

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    @NonNull
    protected V mvpView;
    protected Reference<V> weakReference;

    @NonNull
    @Override
    public void attachView(V view) {
        this.mvpView = view;
        weakReference = new WeakReference<>(mvpView);
        mvpView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new MvpViewHandler(weakReference.get()));
    }

    /**
     * 是否与View建立连接
     */
    protected boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }


    @Override
    public void detachView() {
        this.mvpView = null;
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }


    /**
     * View代理类  防止 页面关闭P异步操作调用V 方法 空指针问题
     */
    private class MvpViewHandler implements InvocationHandler {

        private BaseView mvpView;

        MvpViewHandler(BaseView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                return method.invoke(mvpView, args);
            } //P层不需要关注V层的返回值
            return null;
        }
    }

}
