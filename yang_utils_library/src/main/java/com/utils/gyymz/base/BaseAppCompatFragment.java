//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.utils.gyymz.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsjr.net.R;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.utils.gyymz.utils.T_;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @作者 Yang
 * @创建时间 2018/12/13 17:18
 */
public abstract class BaseAppCompatFragment extends RxFragment {
    public View mView;
    private Unbinder unbinder;
    // Activity对象
    public FragmentActivity mActivity;


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
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_a_base, container, false);
        ViewGroup parent = (ViewGroup) this.mView.getParent();
        if (null != parent) {
            parent.removeView(this.mView);
        }
        addChildView(inflater);
        this.initTitle();
        this.iniPresenter();
        this.afterCreate(savedInstanceState);
        this.initView();
        this.initData();
        if (isRegisteredEventBus()){
            this.registerEvenBus();
        }
        return this.mView;
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


    /**
     * 获得全局的，防止使用getActivity()为空
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    public void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    public void openActivity(Class<?> pClass) {
        Intent intent = new Intent(this.getActivity(), pClass);
        this.startActivity(intent);
    }



    public void requestPermission(String[]... permissions) {
        if (XXPermissions.isHasPermission(getContext(), permissions)) {
            requestPermissionSucceed();
            return;
        }
        XXPermissions.with(getActivity())
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //  .permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                .permission(permissions) //不指定权限则自动获取清单中的危险权限
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            //T_.showCustomToast("获取权限成功");
                            requestPermissionSucceed();
                        } else {
                            T_.showCustomToast("获取权限成功，部分权限未正常授予");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            T_.showCustomToast("被永久拒绝授权，请手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(getActivity());
                        } else {
                            T_.showCustomToast("获取权限失败");
                            //处理权限名字字符串
                            StringBuffer sb = new StringBuffer();
                            for (String in : denied) {
                                sb.append(in);
                                sb.append("\n");
                            }
                            sb.replace(sb.length() - 2, sb.length(), "");
                            //用户点击拒绝并不在询问时候调用
                            showRequestPermissionDialog("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置");
                        }
                    }
                });
    }

    public void isHasPermission() {
        if (XXPermissions.isHasPermission(getContext(), Permission.Group.STORAGE)) {
            T_.showCustomToast("已经获取到权限，不需要再次申请了");
        } else {
            T_.showCustomToast("还没有获取到权限或者部分权限未授予");
        }
    }

    public void gotoPermissionSettings() {
        XXPermissions.gotoPermissionSettings(getContext());
    }


    protected void showRequestPermissionDialog(String msg) {
        //当拒绝了授权后，为提升用户体验，可以以弹窗的方式引导用户到设置中去进行设置
        new AlertDialog.Builder(getActivity())
                .setMessage(msg)
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户到设置中去进行设置
                        gotoPermissionSettings();
                    }
                })
                .setNegativeButton("不行", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }



    public RxFragment getRxActivity() {
        return this;
    }

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }

}
