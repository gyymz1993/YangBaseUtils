package com.utils.gyymz.base.mvp;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.lsjr.net.R;
import com.utils.gyymz.bar.NavigationBarView;
import com.utils.gyymz.bar.OnTitleBarListener;
import com.utils.gyymz.base.BaseAppCompatActivity;
import com.utils.gyymz.even.EventBusUtils;
import com.utils.gyymz.even.EventMessage;
import com.utils.gyymz.vary.ILoadViewFactory;
import com.utils.gyymz.vary.MyLoadViewFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.view.View.VISIBLE;


/**
 * @作者 Yang
 * @创建时间 2018/12/7 11:14
 */
public abstract class MVPBaseActivity<P extends BasePresenter>
        extends BaseAppCompatActivity implements BaseView,
        ViewTreeObserver.OnGlobalLayoutListener, OnTitleBarListener,ILoadViewFactory.ViewRefreshListener {
    public P mPresenter;

    protected ImmersionBar mImmersionBar;//状态栏沉浸
    private NavigationBarView navigationBarView;
    private ILoadViewFactory loadViewFactory;

    @Override
    protected void initPresenter() {
        mPresenter = getDelegateClass();
        if (mPresenter != null) mPresenter.attachView(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisteredEventBus()) {
            EventBusUtils.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) mImmersionBar.destroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        if (mPresenter != null) mPresenter.detachView();
        if (isRegisteredEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    protected abstract P getDelegateClass();

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {

    }

    protected NavigationBarView showTitleText(String context) {
        navigationBarView.setTitle(context);
        navigationBarView.setVisibility(VISIBLE);
        return getNavigationBarView();
    }

    @Override
    public void initNavigationBarView() {
        navigationBarView = findViewById(R.id.id_bar_view);
        navigationBarView.setOnTitleBarListener(this);
        navigationBarView.setVisibility(View.GONE);
        navigationBarView.getLeftView().setVisibility(View.GONE);
        navigationBarView.getRightView().setVisibility(View.GONE);
    }


    protected NavigationBarView showNavigationBarBack() {
        navigationBarView.getLeftView().setVisibility(VISIBLE);
        return getNavigationBarView();
    }

    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }


    /**
     * 右项被点击
     *
     * @param v 被点击的右项View
     */
    @Override
    public void onRightClick(View v) {

    }


    protected NavigationBarView hideNavigationBarBack() {
        navigationBarView.getLeftView().setVisibility(View.GONE);
        return getNavigationBarView();
    }

    @Deprecated
    protected NavigationBarView showNavigationBarCanBack() {
        navigationBarView.getLeftView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }

    protected NavigationBarView showCanBack() {
        navigationBarView.getLeftView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }

    protected NavigationBarView showRightIcon(int icon) {
        showNavigationBarView();
        navigationBarView.setRightIcon(icon);
        navigationBarView.getRightView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
    }

    protected NavigationBarView showRightText(String context) {
        showNavigationBarView();
        navigationBarView.setRightTitle(context);
        navigationBarView.getRightView().setVisibility(View.VISIBLE);
        return getNavigationBarView();
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
    public void requestPermissionSucceed() {

    }


    /**
     * 是否使用沉浸式状态栏
     */
    public boolean isstatusBarColorEnabled() {
        return true;
    }


    /**
     * 获取状态栏沉浸的配置对象
     */
    public ImmersionBar getImmersionBar() {
        return mImmersionBar;
    }


    /**
     * 初始化沉浸式状态栏
     */
    protected ImmersionBar statusBarColorConfig() {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarColorDarkFont());    //默认状态栏字体颜色为黑色

        //     .keyboardEnable(true);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        mImmersionBar            //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .statusBarColor(R.color.statusBarColorColor)     //状态栏颜色，不写默认透明色
                .navigationBarColor(R.color.navigationBarColor);//导航栏颜色，不写默认黑色
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
        return mImmersionBar;
    }


    /**
     * 获取状态栏字体颜色
     */
    public boolean statusBarColorDarkFont() {
        //返回false表示白色字体
        return false;
    }


    @Override
    public void setImmersionBarBlack() {
        //初始化沉浸式状态栏
        if (isstatusBarColorEnabled()) {
            statusBarColorConfig().init();
        }
        //设置标题栏
        if (getTitleBarId() > 0) {
            ImmersionBar.setTitleBar(this, findViewById(getTitleBarId()));
        }
        setImmersionBarBar();
    }


    @Override
    protected void setImmersionBarBar() {

    }

    @Override
    public void initLoadViewFactory(View childView) {
        loadViewFactory = new MyLoadViewFactory(childView,this);
    }

    public ILoadViewFactory getLoadViewFactory() {
        return loadViewFactory;
    }

    public void showLoadingView() {
        loadViewFactory.madeLoadView().showLoading();
    }


    public void showRestoreView() {
        loadViewFactory.madeLoadView().restore();
    }

    @Override
    protected int getTitleBarId() {
        return R.id.id_bar_view;
    }


    /**
     * {@link ViewTreeObserver.OnGlobalLayoutListener}
     */
    @Override
    public void onGlobalLayout() {
    }//不用写任何方法

    @Override
    public void finish() {
        // 隐藏软键盘，避免软键盘引发的内存泄漏
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.finish();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetWorkErrorView() {

    }

    @Override
    public  void onRefreshContent(){

    }

    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
    }

}
