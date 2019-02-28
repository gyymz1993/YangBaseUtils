package com.utils.gyymz.base.mvp;

/**
 * @作者 Yang
 * @创建时间 2018/12/27 14:51
 */
public interface BaseView {

    /**
     * 加载完成时隐藏加载框
     */
    void hideLoading();

    /**
     * 显示空列表的提示
     */
    void showNetWorkErrorView();

}
