package com.utils.gyymz.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.lsjr.net.R;
import com.utils.gyymz.bar.NavigationBarView;
import com.utils.gyymz.bar.OnTitleBarListener;
import com.utils.gyymz.base.BaseAppCompatFragment;
import com.utils.gyymz.even.EventBusUtils;
import com.utils.gyymz.even.EventMessage;
import com.utils.gyymz.vary.ILoadViewFactory;
import com.utils.gyymz.vary.MyLoadViewFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.view.View.VISIBLE;


/**
 * @作者 Yang
 * @创建时间 2019/1/7 12:30
 */

public abstract class MVPBaseFragment<P extends BasePresenterImpl> extends
        BaseAppCompatFragment implements BaseView, OnTitleBarListener {
    private NavigationBarView navigationBarView;
    protected ImmersionBar mImmersionBar;//状态栏沉浸
    public P mPresenter;
    private ILoadViewFactory loadViewFactory;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化沉浸式状态栏
        if (isstatusBarColorEnabled()) {
            statusBarColorConfig().init();
        }
        //设置标题栏
        if (getTitleBarId() > 0) {
            ImmersionBar.setTitleBar(getActivity(), findViewById(getTitleBarId()));
        }
        setImmersionBarBar();

    }

    @Override
    protected void registerEvenBus() {
        EventBusUtils.register(this);
    }

    @Override
    protected void setImmersionBarBar() {
    }

    @Override
    protected int getTitleBarId() {
        return R.id.id_frag_bar_view;
    }


    /**
     * 初始化沉浸式
     */
    protected ImmersionBar statusBarColorConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarColorDarkFont());    //默认状态栏字体颜色为黑色
        return mImmersionBar;
    }


    /**
     * 设置后导致首次进来Fragment无法沉浸  如沉浸实现空方法
     */
    protected void setDefaultNavigationBarColorColor() {
        mImmersionBar.statusBarColor(R.color.statusBarColorColor)     //头部状态栏颜色，不写默认透明色
                .navigationBarColor(R.color.navigationBarColor);//底部导航栏颜色，不写默认黑色
    }

    public ImmersionBar getImmersionBar() {
        return mImmersionBar;
    }


    @Override
    protected void initNavigationBarView() {
        navigationBarView = mView.findViewById(R.id.id_frag_bar_view);
        navigationBarView.setOnTitleBarListener(this);
        navigationBarView.setVisibility(View.GONE);
        navigationBarView.setTitle("我是测试");
        navigationBarView.getLeftView().setVisibility(View.GONE);
    }


    public NavigationBarView showTitleText(String context) {
        navigationBarView.setVisibility(View.VISIBLE);
        navigationBarView.setTitle(context);
        return navigationBarView;
    }


    protected NavigationBarView showRightIcon(int icon) {
        showNavigationBarView();
        navigationBarView.setRightIcon(icon);
        navigationBarView.getRightView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }


    protected NavigationBarView showLefrIcon(int icon) {
        showNavigationBarView();
        navigationBarView.setLeftIcon(icon);
        navigationBarView.getLeftView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }

    protected NavigationBarView showRightText(String context) {
        showNavigationBarView();
        navigationBarView.setRightTitle(context);
        navigationBarView.getRightView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }

    protected NavigationBarView showLeftIvIcon(int icon) {
        showNavigationBarView();
        navigationBarView.setLeftIvIcon(icon);
        navigationBarView.getLeftIv().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }

    protected NavigationBarView hideLeftIvIcon() {
        showNavigationBarView();
        navigationBarView.getLeftIv().setVisibility(View.GONE);
        return getNavigationBarView();
    }

    protected NavigationBarView showNavigationBarBack() {
        navigationBarView.setVisibility(View.VISIBLE);
        navigationBarView.getLeftView().setVisibility(View.VISIBLE);
        return navigationBarView;
    }

    protected NavigationBarView hideNavigationBarBack() {
        navigationBarView.getLeftView().setVisibility(View.INVISIBLE);
        return navigationBarView;
    }

    public NavigationBarView showNavigationBarView() {
        navigationBarView.setVisibility(VISIBLE);
        return navigationBarView;
    }

    public NavigationBarView hideNavigationBarView() {
        navigationBarView.setVisibility(View.GONE);
        return navigationBarView;
    }


    public NavigationBarView getNavigationBarView() {
        return navigationBarView;
    }


    @Override
    public void initLoadViewFactory(View childView) {
        loadViewFactory = new MyLoadViewFactory(childView);
    }

    /**
     * 显示加载布局
     */
    public void showLoadingView() {
        loadViewFactory.madeLoadView().showLoading();
    }


    /**
     * 显示原来布局
     */
    public void showRestoreView() {
        loadViewFactory.madeLoadView().restore();
    }


    public ILoadViewFactory getLoadViewFactory() {
        return loadViewFactory;
    }


    @Override
    protected void iniPresenter() {
        mPresenter = getDelegateClass();
        if (mPresenter != null) mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mImmersionBar != null) mImmersionBar.destroy();
        if (isRegisteredEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    protected abstract P getDelegateClass();


    @Override
    public void requestPermissionSucceed() {

    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarColorDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    public boolean isstatusBarColorEnabled() {
        return true;
    }


    /**
     * 右项被点击
     *
     * @param v 被点击的右项View
     */
    @Override
    public void onRightClick(View v) {

    }

    @Override
    public void onLeftClick(View v) {
        getActivity().onBackPressed();
    }


    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    protected void afterCreate(Bundle var1) {

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }


    @Override
    protected void initView() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetWorkErrorView() {

    }

    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {

    }

//    粘性事件?
//    何为黏性事件呢
//   就是在发送事件之后再订阅该事件也能收到该事件
//    EventBus也提供了这样的功能,有所不同是EventBus会存储所有的Sticky事
//    如果某个事件在不需要再存储则需要手动进行移除,用户通过Sticky的形式发布事件
//    而消费者也需要通过Sticky的形式进行注册
//    当然这种注册除了可以接收 Sticky事件之外和常规的注册功能是一样的，其他类型的事件也会被正常处理。
//    基本使用
//    发布和接收粘性事件一般有如下几步:
//    粘性事件的发布:EventBus.getDefault().postSticky("RECOGNIZE_SONG");

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {

    }


}
