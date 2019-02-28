package com.utils.gyymz;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lsjr.net.BuildConfig;
import com.lsjr.net.R;
import com.utils.gyymz.exception.GlobalExceptionHandler;
import com.utils.gyymz.utils.ActivityStackManager;
import com.utils.gyymz.utils.ActivityUtils;
import com.utils.gyymz.utils.SpUtils;
import com.utils.gyymz.utils.UIUtils;

public class BaseApplicationCompat {
    private static Looper mMainThreadLooper = null;
    private static Handler mMainThreadHandler = null;
    private static int mMainThreadId;
    private static Thread mMainThread = null;
    private static Application mApplication;
    private static BaseApplicationCompat mBaseApplication;
    private static boolean isInit = false;

    private BaseApplicationCompat() {
    }

    public static BaseApplicationCompat instance() {
        if (mBaseApplication == null) {
            synchronized (BaseApplicationCompat.class) {
                if (mApplication == null) {
                    mBaseApplication = new BaseApplicationCompat();
                }
            }
        }
        return mBaseApplication;
    }

    public static Application getApplication() {
        if (mApplication == null) {
            throw new NullPointerException("mApplication 为空");
        }
        return mApplication;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public void initialize(Application application) {
        if (isInit) {
            return;
        }
        mApplication = application;
        if (mApplication != null) {
            mMainThreadLooper = mApplication.getMainLooper();
            mMainThreadHandler = new Handler();
            mMainThreadId = android.os.Process.myTid();
            mMainThread = Thread.currentThread();
            initImageLoader();
            isInit = true;
        }
        initSputils();
        initARouter();
        // Activity 栈管理
       // ActivityUtils.init(application);
        ActivityStackManager.init(application);
        //异常捕获
        GlobalExceptionHandler.getInstance().init(getApplication(), UIUtils.getString(R.string.app_name));
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(mApplication);// 尽可能早，推荐在Application中初始化
    }


    private void initImageLoader() {
    }

    /**
     * 检测内存泄漏
     */
    public void initLeakCanary() {

    }

    private void initSputils() {
        SpUtils.getInstance().init(mApplication);
    }


}


