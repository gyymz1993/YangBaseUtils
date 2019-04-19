package com.utils.gyymz.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: gyymz1993
 * 创建时间：2017/3/27 20:19
 **/
public class T_ {

    protected static final String TAG = "AppToast";
    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 测试时使用
     *
     * @param obj
     */
    @SuppressLint("ShowToast")
    public static void showToastWhendebug(final Object obj) {
        UIUtils.runInMainThread(new Runnable() {

            @Override
            public void run() {
                if (obj == null) {
                    Toast.makeText(UIUtils.getContext(), "对象为空", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UIUtils.getContext(), obj.toString(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    /**
     * 上线运行时使用
     *
     * @param obj
     */
    @SuppressLint("ShowToast")
    public static void showToastReal(final Object obj) {
        UIUtils.runInMainThread(new Runnable() {

            @Override
            public void run() {
                if (obj == null) {
                    Toast.makeText(UIUtils.getContext(), "对象为空", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UIUtils.getContext(), obj.toString(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

    }

    /**
     * 显示Toast
     *
     * @param message
     */
    public static void showCustomToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
    }


    /**
     * 显示Toast
     *
     * @param message
     */
    public static void showCustomToast(String message, boolean isShow) {
        showCustomToast(message);
    }

    public static void customStylesToast(String message) {
        customStylesToast(message, 0, Gravity.BOTTOM);
    }

    public static void customStylesToast(String message, int time, int postion) {

    }

    private void CustomTimeToast() {
        final Toast toast = Toast.makeText(UIUtils.getContext(), "自定义Toast的时间", Toast.LENGTH_LONG);
        toast.show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                //timer.cancel();
            }
        }, 3000);// 5000表示Toast显示时间为5秒

    }

}


