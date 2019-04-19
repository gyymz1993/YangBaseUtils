package com.view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TextViewLayout extends LinearLayout {


    /**
     * 上下分割线，默认隐藏上面分割线
     */
    private View dividerTop, dividerBottom;

    /**
     * 最外层容器
     */
    private LinearLayout llRoot;

    /**
     * 中间的文字内容
     */
    private TextView tvLeftContent;


    /**
     * 整个一行被点击
     */
    public static interface OnRootClickListener {
        void onRootClick(View view);
    }


    /**
     * 右边箭头的点击事件
     */
    public static interface OnArrowClickListener {
        void onArrowClick(View view);
    }

    public TextViewLayout(Context context) {
        super(context);
    }

    public TextViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 初始化各个控件
     */
    public TextViewLayout init() {
        LayoutInflater.from(getContext()).inflate(R.layout.textview_layout_line, this, true);
        llRoot = findViewById(R.id.ll_root);
        dividerTop = findViewById(R.id.divider_top);
        dividerBottom = findViewById(R.id.divider_bottom);
        tvLeftContent =  findViewById(R.id.tv_text_content);
        return this;
    }

    /**
     * 文字 + 箭头
     *
     * @param textContent
     * @return
     */
    public TextViewLayout init(String textContent) {
        init();
        setTextContent(textContent);
        return this;
    }

    /**
     * 默认情况下的样子  icon + 文字 + 右箭头 + 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public TextViewLayout init(int iconRes, String textContent) {
        init();
        showDivider(false, true);
        setTextContent(textContent);
        return this;
    }

    /**
     * 我的页面每一行  icon + 文字 + 右箭头（显示/不显示） + 右箭头左边的文字（显示/不显示）+ 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public TextViewLayout initMine(int iconRes, String textContent) {
        init(iconRes, textContent);
        return this;
    }


    /**
     * icon + 文字 + edit + 下分割线
     *
     * @return
     */
    public TextViewLayout initItemWidthEdit(int iconRes, String textContent, String editHint) {
        init(iconRes, textContent);
        return this;
    }

    //---------------------下面是对每个部分的一些设置     上面是应用中常用场景封装-----------------------//

    /**
     * 设置root的paddingTop 与 PaddingBottom 从而控制整体的行高
     * paddingLeft 与 paddingRight 保持默认 20dp
     */
    public TextViewLayout setRootPaddingTopBottom(int paddintTop, int paddintBottom) {
        llRoot.setPadding(dp2px( 20),
               dp2px(paddintTop),
               dp2px(20),
               dp2px(paddintBottom));
        return this;
    }

    /**
     * 设置root的paddingLeft 与 PaddingRight 从而控制整体的行高
     * <p>
     * paddingTop 与 paddingBottom 保持默认 15dp
     */
    public TextViewLayout setRootPaddingLeftRight(int paddintTop, int paddintRight) {
        llRoot.setPadding(dp2px(paddintTop),
               dp2px( 15),
               dp2px( paddintRight),
               dp2px(15));
        return this;
    }


    public  int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }
    /**
     * 设置上下分割线的显示情况
     *
     * @return
     */
    public TextViewLayout showDivider(Boolean showDividerTop, Boolean showDivderBottom) {
        if (showDividerTop) {
            dividerTop.setVisibility(VISIBLE);
        } else {
            dividerTop.setVisibility(GONE);
        }
        if (showDivderBottom) {
            dividerBottom.setVisibility(VISIBLE);
        } else {
            dividerBottom.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置上分割线的颜色
     *
     * @return
     */
    public TextViewLayout setDividerTopColor(int dividerTopColorRes) {
        dividerTop.setBackgroundColor(getResources().getColor(dividerTopColorRes));
        return this;
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    public TextViewLayout setDividerTopHigiht(int dividerTopHigihtDp) {
        ViewGroup.LayoutParams layoutParams = dividerTop.getLayoutParams();
        layoutParams.height =dp2px(dividerTopHigihtDp);
        dividerTop.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置下分割线的颜色
     *
     * @return
     */
    public TextViewLayout setDividerBottomColor(int dividerBottomColorRes) {
        dividerBottom.setBackgroundColor(getResources().getColor(dividerBottomColorRes));
        return this;
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    public TextViewLayout setDividerBottomHigiht(int dividerBottomHigihtDp) {
        ViewGroup.LayoutParams layoutParams = dividerBottom.getLayoutParams();
        layoutParams.height =dp2px(dividerBottomHigihtDp);
        dividerBottom.setLayoutParams(layoutParams);
        return this;
    }


    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    public TextViewLayout setTextContent(String textContent) {
        tvLeftContent.setText(textContent);
        return this;
    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    public TextViewLayout setTextContentColor(int colorRes) {
        tvLeftContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    public TextViewLayout setTextContentSize(int textSizeSp) {
        tvLeftContent.setTextSize(textSizeSp);
        return this;
    }


    //----------------------下面是一些点击事件

    public TextViewLayout setOnRootClickListener(final OnRootClickListener onRootClickListener, final int tag) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoot.setTag(tag);
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }



    public TextViewLayout setOnRootClickListener(final OnRootClickListener onRootClickListener) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }

}
