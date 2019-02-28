package com.utils.gyymz.base.refresh;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.lsjr.net.R;
import com.lsjr.net.R2;
import com.utils.gyymz.base.mvp.BasePresenter;
import com.utils.gyymz.base.mvp.BasePresenterImpl;
import com.utils.gyymz.base.mvp.BaseView;
import com.utils.gyymz.base.mvp.MVPBaseActivity;
import com.utils.gyymz.base.rvAdapter.BaseRvAdapter;
import com.utils.gyymz.base.rvAdapter.RequestLoadMoreListener;
import com.utils.gyymz.pagination.BasePaginationView;
import com.utils.gyymz.utils.UIUtils;

import butterknife.BindView;

import static com.utils.gyymz.utils.UIUtils.getContext;


/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/7/21 15:14
 */
public abstract class BaseRefreshActivity<T extends BasePresenter> extends MVPBaseActivity<T> implements BasePaginationView, SwipeRefreshLayout.OnRefreshListener, RequestLoadMoreListener {

    @BindView(R2.id.rv_list)
    public RecyclerView mRvRecycleList;
    @BindView(R2.id.swipeLayout)
    public SwipeRefreshLayout mSwipeLayout;
    public BaseRvAdapter baseRefreshAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_swfefresh_activity;
    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRecycleView();
    }


    protected void initRecycleView() {
        //mRvRecycleList = findViewById(R.id.rv_list);
        //mSwipeLayout = findViewById(R.id.swipeLayout);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        baseRefreshAdapter = getBaseRefreshAdapter();
        if (baseRefreshAdapter == null) {
            return;
        }
        mRvRecycleList.setHasFixedSize(true);
        mRvRecycleList.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRvRecycleList.addItemDecoration(mDividerItemDecoration);
        // 第一种，直接取消动画
        RecyclerView.ItemAnimator animator = mRvRecycleList.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mRvRecycleList.getItemAnimator().setChangeDuration(0);// 通过设置动画执行时间为0来解决闪烁问题
        baseRefreshAdapter.setOnLoadMoreListener(this, mRvRecycleList);
        // baseRefreshAdapter.openLoadAnimation();
        mRvRecycleList.setAdapter(baseRefreshAdapter);


    }


    public abstract BaseRvAdapter getBaseRefreshAdapter();

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetWorkErrorView() {
        getLoadViewFactory().madeLoadView().showNetError();
    }

    @Override
    public void onRefresh() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeLayout != null)
                    mSwipeLayout.setRefreshing(false);
            }
        }, 1000);
    }


    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void noMoreData() {
        getBaseRefreshAdapter().loadMoreEnd(false);
        mSwipeLayout.setEnabled(true);
    }

    @Override
    public void loadFaild(String error) {
        mSwipeLayout.setEnabled(true);
        mSwipeLayout.setRefreshing(false);
        getLoadViewFactory().madeLoadView().showEmpty();
    }


    @Override
    public void hasNoData() {
        getLoadViewFactory().madeLoadView().showEmpty();
    }



    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onAllPageLoaded() {

    }

}
