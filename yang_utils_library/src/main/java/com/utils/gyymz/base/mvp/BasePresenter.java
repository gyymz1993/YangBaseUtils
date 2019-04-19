package com.utils.gyymz.base.mvp;


/**
* @作者    Yang
* @创建时间 2018/12/27 14:55
*
*/

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

}
