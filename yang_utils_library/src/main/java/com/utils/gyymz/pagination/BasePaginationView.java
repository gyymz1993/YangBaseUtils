package com.utils.gyymz.pagination;

import com.utils.gyymz.base.mvp.BaseView;


public interface BasePaginationView extends BaseView {
    /**
     * 一次页面加载完成操作
     */
    void onLoadingCompleted();

    /**
     * 所有页面均加载完成
     */
    void onAllPageLoaded();


    void onLoadMoreRequested();

    void noMoreData();

    void hasNoData();

    void loadFaild(String error);


}
