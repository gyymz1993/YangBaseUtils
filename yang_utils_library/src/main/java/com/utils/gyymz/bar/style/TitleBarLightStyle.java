package com.utils.gyymz.bar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.lsjr.net.R;
import com.utils.gyymz.utils.UIUtils;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/TitleBar
 * time   : 2018/08/20
 * desc   : 默认日间主题样式实现（布局属性：app:bar_style="light"）
 */
public class TitleBarLightStyle extends BaseTitleBarStyle {

    public TitleBarLightStyle(Context context) {
        super(context);
    }

    @Override
    public Drawable getBackground() {
        return new ColorDrawable(UIUtils.getColor(R.color.gytheme));
    }

    @Override
    public Drawable getBackIcon() {
        return getDrawable(R.drawable.cx_fh);
    }

    @Override
    public int getLeftColor() {
        return 0xFFFFFFFF;
    }

    @Override
    public int getTitleColor() {
        return 0xFFFFFFFF;
    }

    @Override
    public int getRightColor() {
        return 0xFFFFFFFF;
    }

    @Override
    public boolean isLineVisible() {
        return true;
    }

    @Override
    public Drawable getLineDrawable() {
        return new ColorDrawable(0xFFECECEC);
    }

    @Override
    public int getLineSize() {
        return 1;
    }

    @Override
    public Drawable getLeftBackground() {
        return getDrawable(R.drawable.bar_selector_selectable_white);
    }

    @Override
    public Drawable getRightBackground() {
        return getDrawable(R.drawable.bar_selector_selectable_white);
    }

    @Override
    public float getTitleSize() {
        return super.getTitleSize();
    }
}