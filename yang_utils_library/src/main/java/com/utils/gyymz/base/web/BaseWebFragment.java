package com.utils.gyymz.base.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lsjr.net.R;
import com.lsjr.net.R2;
import com.utils.gyymz.base.mvp.MVPBaseFragment;
import com.utils.gyymz.utils.L_;
import com.utils.gyymz.utils.WebViewLifecycleUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


/**
 * 创建人：$ gyymz1993
 * 创建时间：2017/10/16 10:09
 */

public abstract class BaseWebFragment extends MVPBaseFragment {

    @BindView(R2.id.pb_web_progress)
    ProgressBar mProgressBar;
    @BindView(R2.id.wv_web_view)
    public WebView mWebView;
    public static BaseWebFragment instance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_web;
    }

    public WebView getmWebView() {
        return mWebView;
    }

    @Override
    protected void afterCreate(Bundle var1) {
        instance = this;
        initData();
    }

    @Override
    protected void initView() {
        // 不显示滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebContentsDebuggingEnabled(true);

        WebSettings settings = mWebView.getSettings();
        // 允许文件访问
        settings.setAllowFileAccess(true);
        // 支持javaScript
        settings.setJavaScriptEnabled(true);
        // 允许网页定位
        settings.setGeolocationEnabled(true);
        // 允许保存密码
        settings.setSavePassword(true);


        /**
         * 允许缩放网页
         */
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
        // 支持播放gif动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 解决Android 5.0上Webview默认不允许加载Http与Https混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 加快HTML网页加载完成的速度，等页面finish再加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
    }

    @Override
    protected void initData() {
        //启动方式
//        IntentExtraUtils.getInstance(BaseWebFragment.class)
//                .putString("https://github.com/getActivity/")
//                .startActivity(getActivity());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        // String url = IntentExtraUtils.getFragmentInstance(getActivity()).getString();
        mWebView.loadUrl(getUrl());
    }

    public abstract String getUrl();

//       public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            // 后退网页并且拦截该事件
//            mWebView.goBack();
//            return true;
//        }
//        return true;
// }

    public boolean onBackPressed() {
        if (mWebView == null) return false;
        if (mWebView.canGoBack()) {
            // 返回上一页面
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebView.goBack();
            return true;
        }
        return false;
    }


    @Override
    public void onResume() {
        WebViewLifecycleUtils.onResume(mWebView);
        super.onResume();
    }

    @Override
    public void onPause() {
        WebViewLifecycleUtils.onPause(mWebView);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        WebViewLifecycleUtils.onDestroy(mWebView);
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {

        // 网页加载错误时回调，这个方法会在onPageFinished之前调用
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {

        }

        // 开始加载网页
        @Override
        public void onPageStarted(final WebView view, final String url, Bitmap favicon) {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
           // showLoadingView();
        }

        // 完成加载网页
        @Override
        public void onPageFinished(WebView view, String url) {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }

//            onReceivedTitle() 没被调用的解决
//            写在 onPageFinished() 里 就可以了

            String title=view.getTitle();
            if (title != null) {
                setTitleBar(title);
            }
         //   showRestoreView();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);注意一定要去除这行代码，否则设置无效。
            // handler.cancel();// Android默认的处理方式
            handler.proceed();// 接受所有网站的证书
            // handleMessage(Message msg);// 进行其他处理
        }

        // 跳转到其他链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {

            String scheme = Uri.parse(url).getScheme();
            if (scheme != null) {
                scheme = scheme.toLowerCase();
            }
            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                if (url.contains("showPlant")) {
                    //如果是装置图就开启新界面全屏
                    openNewActivity(url);
                } else {
                    mWebView.loadUrl(url);
                }
            }
            // 已经处理该链接请求
            return true;
        }
    }

    private Map<String, String> titleMap = new HashMap<>();
    protected abstract void openNewActivity(String url);

    private class MyWebChromeClient extends WebChromeClient {

        // 收到网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            L_.e("onReceivedTitle"+title);
            if (title != null) {
                setTitleBar(title);
            }
        }

        // 收到加载进度变化
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressBar != null)
                mProgressBar.setProgress(newProgress);
        }
    }

    public abstract void setTitleBar(String title);
}
