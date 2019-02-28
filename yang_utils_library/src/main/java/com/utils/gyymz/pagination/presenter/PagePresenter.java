package com.utils.gyymz.pagination.presenter;


import com.utils.gyymz.pagination.BasePaginationView;
import com.utils.gyymz.pagination.StrategyFactory;
import com.utils.gyymz.pagination.core.BasePaginationPresenter;
import com.utils.gyymz.pagination.strategy.PageStrategy;

public abstract class PagePresenter<T extends BasePaginationView> extends BasePaginationPresenter<T> {

    private PageStrategy pageStrategy;

    public PagePresenter() {
        setPaginationStrategy(StrategyFactory.getStrategy(StrategyFactory.PageStrategy));
        pageStrategy = (PageStrategy) strategy;
    }

    /** 获取当前页数下标 **/
    public int getPageNo() {
        return pageStrategy.getCondition().getPageNo();
    }

    /** 获取当前每页个数 **/
    public int getPageSize() {
        return pageStrategy.getCondition().getPageSize();
    }

    /** 设置每页个数 **/
    public void setPageSize(int size) {
        pageStrategy.setPageSize(size);
    }
}
