package com.view.utils.tab;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.view.utils.R;

import java.util.Iterator;
import java.util.List;

public class BottomTabView extends LinearLayout {
    private int lastPosition = -1;
    private List<BottomTabView.TabItemView> tabItemViews;
    BottomTabView.OnTabItemSelectListener onTabItemSelectListener;
    BottomTabView.OnSecondSelectListener onSecondSelectListener;

    public BottomTabView(Context context) {
        super(context);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(
            api = 11
    )
    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTabItemViews(List<BottomTabView.TabItemView> tabItemViews) {
        this.setTabItemViews(tabItemViews, (View) null);
    }

    public void setTabItemViews(List<BottomTabView.TabItemView> tabItemViews, View centerView) {
        if (this.tabItemViews != null) {
            throw new RuntimeException("不能重复设置！");
        } else if (tabItemViews != null && tabItemViews.size() >= 2) {
            this.tabItemViews = tabItemViews;

            BottomTabView.TabItemView tab;
            for (int i = 0; i < tabItemViews.size(); i++) {
                if (centerView != null && i == tabItemViews.size() / 2) {
                    this.addView(centerView);
                }
                tab = (BottomTabView.TabItemView) tabItemViews.get(i);
                this.addView(tab);
                final int finalI = i;
                tab.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (finalI == BottomTabView.this.lastPosition) {
                            if (BottomTabView.this.onSecondSelectListener != null) {
                                BottomTabView.this.onSecondSelectListener.onSecondSelect(finalI);
                            }

                        } else {
                            BottomTabView.this.updatePosition(finalI);
                            if (BottomTabView.this.onTabItemSelectListener != null) {
                                BottomTabView.this.onTabItemSelectListener.onTabItemSelect(finalI);
                            }

                        }
                    }
                });
            }

            Iterator var6 = tabItemViews.iterator();

            while (var6.hasNext()) {
                tab = (BottomTabView.TabItemView) var6.next();
                tab.setStatus(2);
            }

            this.updatePosition(0);
        } else {
            throw new RuntimeException("TabItemView 的数量必须大于2！");
        }
    }

    public void updatePosition(int position) {
        if (this.lastPosition != position) {
            ((BottomTabView.TabItemView) this.tabItemViews.get(position)).setStatus(1);
            if (this.lastPosition != -1) {
                ((BottomTabView.TabItemView) this.tabItemViews.get(this.lastPosition)).setStatus(2);
            }

            this.lastPosition = position;
        }

    }

    public void setOnTabItemSelectListener(BottomTabView.OnTabItemSelectListener onTabItemSelectListener) {
        this.onTabItemSelectListener = onTabItemSelectListener;
    }

    public void setOnSecondSelectListener(BottomTabView.OnSecondSelectListener onSecondSelectListener) {
        this.onSecondSelectListener = onSecondSelectListener;
    }

    public static class TabItemView extends LinearLayout {
        public static final int PRESS = 1;
        public static final int DEFAULT = 2;
        public String title;
        public int colorDef;
        public int colorPress;
        public static boolean NOIMGAE = false;
        public int iconResDef = -1;
        public int iconResPress = -1;
        public TextView tvTitle;
        public ImageView ivIcon;
        public LinearLayout unreadLayout;
        public TextView tvUnread;

        public TabItemView(Context context, String title, int colorDef, int colorPress, int iconResDef, int iconResPress) {
            super(context);
            this.title = title;
            this.colorDef = colorDef;
            this.colorPress = colorPress;
            this.iconResDef = iconResDef;
            this.iconResPress = iconResPress;
            this.init();
        }

        public void setPromptNum(int num) {
            if (num > 0) {
                this.unreadLayout.setVisibility(VISIBLE);
                this.tvUnread.setText(num + "");
            } else {
                this.unreadLayout.setVisibility(GONE);
            }

        }

        public void init() {
            View view = LayoutInflater.from(super.getContext()).inflate(R.layout.view_tab_item, this);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            this.unreadLayout = (LinearLayout) view.findViewById(R.id.unread_layout);
            this.tvUnread = (TextView) view.findViewById(R.id.tv_unread);
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            layoutParams.weight = 1.0F;
            view.setLayoutParams(layoutParams);
            this.tvTitle.setText(this.title);
        }

        public static void setNOIMGAE(boolean NOIMGAE) {
            NOIMGAE = NOIMGAE;
        }

        public void setStatus(int status) {
            this.tvTitle.setTextColor(ContextCompat.getColor(super.getContext(), status == 1 ? this.colorPress : this.colorDef));
            if (this.iconResPress != -1 && this.iconResDef != -1) {
                this.ivIcon.setImageResource(status == 1 ? this.iconResPress : this.iconResDef);
            }

        }
    }

    public interface OnTabItemSelectListener {
        void onTabItemSelect(int var1);
    }

    public interface OnSecondSelectListener {
        void onSecondSelect(int var1);
    }
}
