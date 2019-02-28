package com.utils.gyymz.wiget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsjr.net.R;

import static com.utils.gyymz.utils.UIUtils.postDelayed;


/**
 * 加载对话框工具类
 */

public class LoadingDialog extends Dialog {
    private static final long TIME_DISMISS_DEFAULT = 1500;
    private static int theme = R.style.dialog;
    private View contentView;
    private TextView tv_loadText;
    private ImageView iv_loadImage;
    private AppCompatActivity mContext;
    private GifView circularBar;

    public LoadingDialog(Context context) {
        super(context, theme);
        this.mContext = (AppCompatActivity) context;
        init();
        setMessage(null);
    }


    private void init() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        setContentView(contentView);
        setCanceledOnTouchOutside(false);//调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用；
        setCancelable(false);// 调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        //circularBar = contentView.findViewById(R.id.gif_loading);
       // circularBar.setMovieResource(R.raw.loading_one_touch);
        tv_loadText = contentView.findViewById(R.id.tv_msg);
        iv_loadImage = contentView.findViewById(R.id.iv_load_image);
    }


    /**
     * 显示加载的ProgressDialog
     */
    public void showProgressDialog() {
        iv_loadImage.setVisibility(View.GONE);
        tv_loadText.setVisibility(View.GONE);
        show();
    }

    /**
     * 显示有加载文字ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param text 需要显示的文字
     */
    public void showProgressDialogWithText(String text) {
        if (TextUtils.isEmpty(text)) {
            showProgressDialog();
        } else {
            iv_loadImage.setVisibility(View.GONE);
            tv_loadText.setText(text);
            tv_loadText.setVisibility(View.VISIBLE);
            show();
        }
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载成功需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressSuccess(String message, long time) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        //iv_loadImage.setBackgroundResource(R.drawable.ic_load_success_white);
        iv_loadImage.setVisibility(View.VISIBLE);
        tv_loadText.setText(message);
        tv_loadText.setVisibility(View.VISIBLE);
        show();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, time);
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressSuccess(String message) {
        showProgressSuccess(message, TIME_DISMISS_DEFAULT);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载失败需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressFail(String message, long time) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        //  pb_loadProgress.setVisibility(View.GONE);
        // iv_loadImage.setBackgroundResource(R.drawable.ic_load_fail_white);
        iv_loadImage.setVisibility(View.VISIBLE);
        tv_loadText.setText(message);
        tv_loadText.setVisibility(View.VISIBLE);
        show();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, time);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressFail(String message) {
        showProgressFail(message, TIME_DISMISS_DEFAULT);
    }

    /**
     * 隐藏加载的ProgressDialog
     */
    public void dismissProgressDialog() {
        dismiss();
    }


    public void setMessage(String message) {
        if (message == null) {
            if (tv_loadText.getVisibility() == View.VISIBLE)
                tv_loadText.setVisibility(View.GONE);
            return;
        }

        if (tv_loadText.getVisibility() == View.GONE)
            tv_loadText.setVisibility(View.VISIBLE);
        tv_loadText.setText(message);
    }

    public void setMessage(int message) {
        if (tv_loadText.getVisibility() == View.GONE)
            tv_loadText.setVisibility(View.VISIBLE);
        tv_loadText.setText(message);
    }

    @Override
    public void show() {
        try {
            if (!mContext.isFinishing()) {
                super.show();
            }
        }catch (Exception e){

        }

    }

    @Override
    public void dismiss() {

        try {
            if (!mContext.isFinishing()) {
                super.dismiss();
            }
        }catch (Exception e){

        }

    }
}
