package com.utils.gyymz.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsjr.net.R;
import com.noober.background.BackgroundLibrary;
import com.utils.gyymz.base.rxManager.BaseRxAppCompatActivity;
import com.utils.gyymz.utils.T_;
import com.utils.gyymz.vary.ILoadViewFactory;
import com.utils.gyymz.vary.MyLoadViewFactory;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者 Yang
 * @创建时间 2018/12/7 11:14
 */
public abstract class BaseAppCompatActivity extends BaseRxAppCompatActivity {

    public Unbinder unbinder;
    public View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initPresenter();
        afterCreate(savedInstanceState);
        setImmersionBarBlack();
        initOrientation();
        initView();
        initTitle();
        initData();
    }

    protected abstract void setImmersionBarBlack();


    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        //如果没有指定屏幕方向，则默认为竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    protected abstract void initPresenter();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化头部
     */
    protected abstract void initTitle();


    //标题栏id
    protected abstract int getTitleBarId();


    protected abstract void setImmersionBarBar();


    @SuppressLint("InflateParams")
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mRootView = getLayoutInflater().inflate(R.layout.activity_a_base, null);
        super.setContentView(mRootView);
        initDefaultView(layoutResID);
    }


    /**
     * 初始化默认布局的View
     *
     * @param layoutResId 子View的布局id
     */
    @SuppressLint("RestrictedApi")
    private void initDefaultView(int layoutResId) {
        FrameLayout container = findViewById(R.id.fl_activity_child_container);
        initNavigationBarView();
        View childView = LayoutInflater.from(this).inflate(layoutResId, null);
        container.addView(childView, 0);
        initLoadViewFactory(childView);

    }


    public abstract void initNavigationBarView();

    public abstract void initLoadViewFactory(View childView);


    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }



    @Override
    protected void onDestroy() {
        //移除view绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
    }

    /*跳转到登录页面  登录成功回调到刚刚页面*/
    public void loginToServer(Class<?> c, Activity resultActivcity) {
        Intent loginIntent = new Intent(this, resultActivcity.getClass());
        loginIntent.putExtra("", c);
        startActivity(loginIntent);
        finish();
    }

    public void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    public void jumpToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void jumpToActivityAndClearTask(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void jumpToActivityAndClearTop(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void toastCenter(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public interface OnTopBarClickListener {
        void onClick();
    }


    /**
     * 设置全屏模式，并将状态栏设置为透明，支持4.4及以上系统
     */
    public void setTranslucentstatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            setFullScreen();
        }
    }

    /**
     * 设置状态栏为浅色模式，状态栏上的图标都会变为深色。仅支持6.0及以上系统
     */
    public void setLightstatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置全屏模式
     */
    public void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 设置系统状态颜色，仅支持6.0及以上系统
     */
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(color);
        }
    }


    public void requestPermission(String[]... permissions) {
        if (XXPermissions.isHasPermission(this, permissions)) {
            requestPermissionSucceed();
            return;
        }
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                .permission(permissions) //不指定权限则自动获取清单中的危险权限
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            // T_.showCustomToast("获取权限成功");
                            //hasPermissionSucceed();
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
                            //处理权限名字字符串
                            StringBuffer sb = new StringBuffer();
                            for (String in : denied) {
                                sb.append(in);
                                sb.append("\n");
                            }
                            sb.replace(sb.length() - 2, sb.length(), "");
                            //用户点击拒绝并不在询问时候调用
                            showRequestPermissionDialog("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置");
                            //XXPermissions.gotoPermissionSettings(BaseAppCompatActivity.this);
                        } else {
                            T_.showCustomToast("获取权限失败");
                        }
                    }
                });
    }

    public void isHasPermission() {
        if (XXPermissions.isHasPermission(BaseAppCompatActivity.this, Permission.Group.STORAGE)) {
            T_.showCustomToast("已经获取到权限，不需要再次申请了");
        } else {
            T_.showCustomToast("还没有获取到权限或者部分权限未授予");
        }
    }

    public void gotoPermissionSettings() {
        XXPermissions.gotoPermissionSettings(BaseAppCompatActivity.this);
    }


    protected void showRequestPermissionDialog(String msg) {
        //当拒绝了授权后，为提升用户体验，可以以弹窗的方式引导用户到设置中去进行设置
        new AlertDialog.Builder(this)
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


    public abstract void requestPermissionSucceed();


    protected BaseRxAppCompatActivity getActivity() {
        return this;
    }
}
