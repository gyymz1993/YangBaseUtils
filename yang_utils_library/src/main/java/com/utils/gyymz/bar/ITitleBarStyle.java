package com.utils.gyymz.bar;

import android.graphics.drawable.Drawable;

/**
* @作者    Yang
* @创建时间 2019/2/13 13:33
*  github : https://github.com/getActivity/TitleBar
*/
public interface ITitleBarStyle {

    int getTitleBarHeight(); /** 标题栏高度（默认为ActionBar的高度）**/
    /** 背景颜色
     *
     * @return
     */
    Drawable getBackground();

    /**
     *  返回按钮图标
     * @return
     */
    Drawable getBackIcon();

    /**
     * 左边文本颜色
     * @return
     */
    int getLeftColor();

    /**
     * 标题文本颜色
     * @return
     */
    int getTitleColor(); //

    /**
     * 右边文本颜色
     * @return
     */
    int getRightColor(); //

    /**
     * 左边文本大小
     * @return
     */
    float getLeftSize(); //

    /**
     * 标题文本大小
     * @return
     */
    float getTitleSize(); //

    /**
     * 右边文本大小
     * @return
     */
    float getRightSize(); //

    /**
     * 分割线是否可见
     * @return
     */
    boolean isLineVisible(); //

    /**
     * 分割线背景颜色
     * @return
     */
    Drawable getLineDrawable(); //

    /**
     * 分割线的大小
     * @return
     */
    int getLineSize(); //

    /**
     * 左边背景资源
     * @return
     */
    Drawable getLeftBackground(); //

    /**
     * 右边背景资源
     * @return
     */
    Drawable getRightBackground(); //
}