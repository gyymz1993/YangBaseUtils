package com.utils.gyymz.base.refresh;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjr.net.R;
import com.lsjr.net.R2;
import com.utils.gyymz.base.mvp.BasePresenterImpl;
import com.utils.gyymz.base.mvp.BaseView;
import com.utils.gyymz.base.mvp.MVPBaseFragment;
import com.utils.gyymz.base.rvAdapter.BaseRvAdapter;
import com.utils.gyymz.base.rvAdapter.RequestLoadMoreListener;
import com.utils.gyymz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @作者 Yang
 * @创建时间 2018/12/29 16:17
 */
public abstract class BaseRefreshFragment<T extends BasePresenterImpl> extends MVPBaseFragment<T> implements BaseView, SwipeRefreshLayout.OnRefreshListener, RequestLoadMoreListener {

    public BaseRvAdapter baseRefreshAdapter;
    @BindView(R2.id.rv_list)
    public  RecyclerView mRvRecycleList;
    @BindView(R2.id.swipeLayout)
    public SwipeRefreshLayout mSwipeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.base_swfefresh_activity;
    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initRecycleView();
    }


    protected void initRecycleView() {
       // mRvRecycleList = mView.findViewById(R.id.rv_list);
        //mSwipeLayout = mView.findViewById(R.id.swipeLayout);
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
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRvRecycleList.addItemDecoration(mDividerItemDecoration);
        // 第一种，直接取消动画
        RecyclerView.ItemAnimator animator = mRvRecycleList.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mRvRecycleList.getItemAnimator().setChangeDuration(0);// 通过设置动画执行时间为0来解决闪烁问题
        /**是否需要多页数据  加载更多***/
        baseRefreshAdapter.setOnLoadMoreListener(this, mRvRecycleList);
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


    public void noMoreData() {
        getBaseRefreshAdapter().loadMoreEnd(false);
        mSwipeLayout.setEnabled(true);
    }


    public void loadFaild(String error) {
        mSwipeLayout.setEnabled(true);
        mSwipeLayout.setRefreshing(false);
        getLoadViewFactory().madeLoadView().showEmpty();
    }


}
