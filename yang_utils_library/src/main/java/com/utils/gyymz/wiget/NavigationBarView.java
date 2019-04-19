package com.utils.gyymz.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsjr.net.R;
import com.utils.gyymz.utils.UIUtils;


/**
 * @author: gyymz1993
 * 创建时间：2017/4/26 16:54
 **/
public class NavigationBarView extends RelativeLayout {
    public String titleText;
    private String letfText;
    private String rightText;
    private int letfTextColor;
    private int rightTextColor;
    private Drawable leftIcon;
    private Drawable rightIcon;
    private int bgColor;
    private int height;
    private int titleColor;
    private TextView leftTv;
    private ImageView leftIv;
    private TextView rightTv;
    private TextView titleTv;
    private ImageView rightImg;
    public View mView;


    public enum NavigationViewType {
        LEFT_TEXT, RIGHT_TEXT, LEFT_IV, RIGHT_IV, CONTEXT_TV
    }


    public NavigationBarView(Context context) {
        this(context, null);
    }

    public NavigationBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = View.inflate(context, R.layout.layout_navigation_default, this);
        // LayoutInflater.from(getContext()).inflate(R.layout.layout_navigation_default, this, false);
        // 将view添加。
        initTypedArray(context, attrs);
        initView();
    }



    public void initTypedArray(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {// 得到自定义属性
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBarItem);
            leftIcon = ta.getDrawable(R.styleable.TopBarItem_leftIcon1);
            rightIcon = ta.getDrawable(R.styleable.TopBarItem_rgihtIcon1);
            titleText = ta.getString(R.styleable.TopBarItem_titles);
            letfText = ta.getString(R.styleable.TopBarItem_leftText1);
            rightText = ta.getString(R.styleable.TopBarItem_rightText1);
            height = ta.getInteger(R.styleable.TopBarItem_topBarheight1, UIUtils.getDimen(R.dimen.nav_title_height));
            bgColor = ta.getColor(R.styleable.TopBarItem_backgrounds, Color.parseColor("#FFFFFFFF"));
            letfTextColor = ta.getColor(R.styleable.TopBarItem_leftColor1, Color.parseColor("#ffffff"));
            rightTextColor = ta.getColor(R.styleable.TopBarItem_rightColor1, Color.parseColor("#ffffff"));
            ta.recycle();
        }
    }


    protected void initView() {
        // 将view添加。
        leftTv = mView.findViewById(R.id.tv_left);
        leftIv = mView.findViewById(R.id.iv_left);
        rightTv = mView.findViewById(R.id.tv_right);
        rightImg = mView.findViewById(R.id.iv_right);
        titleTv = mView.findViewById(R.id.title_tv);

        if (letfText != null) {
            leftTv.setText(letfText);
        }
        if (rightText != null) {
            rightTv.setText(rightText);
        }
        if (leftIcon != null) {
            leftIv.setImageDrawable(leftIcon);
        }
        if (rightIcon != null) {
            rightImg.setImageDrawable(rightIcon);
        }
        if (titleText != null) {
            titleTv.setText(titleText);
        }
        if (height != 0) {
            setTorBarTHeight(height);
        }
        if (titleColor != 0) {
            titleTv.setTextColor(titleColor);
        }
        if (bgColor != 0) {
            setBackgroundColor(bgColor);
        }
    }


    public NavigationBarView setTorBarTHeight(int height) {
        RelativeLayout relativeLayout = viewFindById(R.id.title_bar);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.width = UIUtils.WHD()[0];
        layoutParams.height = height;
        return this;
    }


    public <T extends View> T viewFindById(int id) {
        return (T) getRootView().findViewById(id);
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param visible True for VISIBLE, false for GONE.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setVisible(NavigationViewType navigationViewType, boolean visible) {
        View view = navigationViewID(navigationViewType);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param listener The on click listener;
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setOnClickListener(NavigationViewType navigationViewType, View.OnClickListener listener) {
        View view = navigationViewID(navigationViewType);
        view.setOnClickListener(listener);
        return this;
    }


    /**
     * Will set the text of a TextView.
     *
     * @param value The text to put in the text view.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setText(NavigationViewType navigationViewType, String value) {
        TextView view = navigationViewID(navigationViewType);
        view.setText(value);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param imageResId The image resource id.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setImageResource(NavigationViewType navigationViewType, int imageResId) {
        ImageView view = navigationViewID(navigationViewType);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * Will set background color of a view.
     *
     * @param color A color, not a resource id.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setBackgroundColor(NavigationViewType navigationViewType, int color) {
        View view = navigationViewID(navigationViewType);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     *
     * @param backgroundRes A resource to use as a background.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setBackgroundRes(NavigationViewType navigationViewType, int backgroundRes) {
        View view = navigationViewID(navigationViewType);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param textColor The text color (not a resource id).
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setTextColor(NavigationViewType navigationViewType, int textColor) {
        TextView view = navigationViewID(navigationViewType);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param textColorRes The text color resource id.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setTextColorRes(NavigationViewType navigationViewType, int textColorRes) {
        TextView view = navigationViewID(navigationViewType);
        view.setTextColor(UIUtils.getColor(textColorRes));
        return this;
    }

    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param drawable The image drawable.
     * @return The BaseAdapterHelper for chaining.
     */
    public NavigationBarView setImageDrawable(NavigationViewType navigationViewType, Drawable drawable) {
        ImageView view = navigationViewID(navigationViewType);
        view.setImageDrawable(drawable);
        return this;
    }


    public <T extends View> T navigationViewID(NavigationViewType navigationViewType) {
        int viewId;
        if (navigationViewType == NavigationViewType.LEFT_IV) {
            viewId = R.id.iv_left;
        } else if (navigationViewType == NavigationViewType.LEFT_TEXT) {
            viewId = R.id.tv_left;
        } else if (navigationViewType == NavigationViewType.RIGHT_IV) {
            viewId = R.id.iv_right;
        } else if (navigationViewType == NavigationViewType.RIGHT_TEXT) {
            viewId = R.id.tv_right;
        } else {
            viewId = R.id.title_tv;
        }
        return viewFindById(viewId);
    }

}
