package com.utils.gyymz.base.lazy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lsjr.net.R;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.utils.gyymz.base.BaseAppCompatFragment;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : Fragment 懒加载基类
 */
public abstract class BaseLazyFragment extends RxFragment {

    private Unbinder unbinder;
    // Activity对象
    public FragmentActivity mActivity;
    // 根布局
    protected View mView;
    // 当前是否进行过懒加载
    private boolean isLazyLoad = false;
    // 当前 Fragment 是否可见
    private boolean isFragmentVisible;

    /**
     * 获得全局的，防止使用getActivity()为空
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    /**
     * 获取Activity，防止出现 getActivity() 为空
     */
    public FragmentActivity getFragmentActivity() {
        return mActivity;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_a_base, container, false);
        ViewGroup parent = (ViewGroup) this.mView.getParent();
        if (null != parent) {
            parent.removeView(this.mView);
        }
        addChildView(inflater);
        this.afterCreate(savedInstanceState);
        this.initTitle();
        return this.mView;
    }




    public void onDestroyView() {
        //移除view绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    /**
     * 添加子Fragment的布局文件
     *
     * @param inflater
     */
    private void addChildView(LayoutInflater inflater) {
        FrameLayout container = mView.findViewById(R.id.fl_activity_child_container);
        initNavigationBarView();
        if (getLayoutId() == 0) return;
        View child = inflater.inflate(getLayoutId(), null);
        container.addView(child, 0);
        this.unbinder = ButterKnife.bind(this, this.mView);
        initLoadViewFactory(child);

    }


    @Override
    public View getView() {
        return mView;
    }

    protected boolean isLazyLoad() {
        return isLazyLoad;
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFragmentVisible && !isLazyLoad && getView() != null) {
            isLazyLoad = true;
            init();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isFragmentVisible = isVisibleToUser;
        if (isVisibleToUser && getView() != null) {
            if (!isLazyLoad) {
                isLazyLoad = true;
                init();
            }else {
                // 从不可见到可见
                onRestart();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 当前 Fragment 是否可见
     */
    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 跟 Activity 的同名方法一样
     */
    protected void onRestart() {
        // 从可见的状态变成不可见状态，再从不可见状态变成可见状态时回调
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //解决java.lang.IllegalStateException: Activity has been destroyed 的错误
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void init() {

        this.iniPresenter();
        this.initView();
        this.initData();
        if (isRegisteredEventBus()){
            this.registerEvenBus();
        }
    }

    protected abstract void iniPresenter();
    protected abstract void initData();
    protected abstract void initView();
    protected abstract void initTitle();
    protected abstract int getLayoutId();
    //标题栏id
    protected abstract int getTitleBarId();
    protected abstract void afterCreate(Bundle var1);

    /**
     * 获取权限成功
     */
    public abstract void requestPermissionSucceed();
    public abstract void initLoadViewFactory(View childView);
    protected abstract void initNavigationBarView();
    protected abstract void setImmersionBarBar();

    protected abstract void registerEvenBus();

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }


    /**
     * 根据资源id获取一个View
     */
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) getView().findViewById(id);
    }

    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }

    /**
     * 跳转到其他Activity
     *
     * @param cls          目标Activity的Class
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getContext(), cls));
    }

    /**
     * 跳转到其他 Activity 并销毁当前 Activity
     *
     * @param cls       目标Activity的Class
     */
    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivity(cls);
        mActivity.finish();
    }

    /**
     * 获取系统服务
     */
    public Object getSystemService(@NonNull String name) {
        return mActivity.getSystemService(name);
    }

    /**
     * Fragment返回键被按下时回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //默认不拦截按键事件，传递给Activity
        return false;
    }
}