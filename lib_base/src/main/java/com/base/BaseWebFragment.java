package com.base;


import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.socks.library.KLog;
import com.utils.NetUtils;
import com.view.WebViewForRefresh;

import chen.lib_base.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseWebFragment extends BaseFragment {

    private static final String URL = "URL";

    protected WebViewForRefresh mWebView;
    private SwipeRefreshLayout srf;
    private WebSettings mWebSettings;
    private View mEmptyView;
    private String mUrl, mFailingUrl;
    private int count = 0;

    public BaseWebFragment() {
    }

    public static BaseWebFragment newInstance(String url) {
        BaseWebFragment fragment = new BaseWebFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getFragmentArguments(Bundle args) {
        mUrl = args.getString(URL);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_base_web;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        mWebView = view.findViewById(R.id.wv_base);
        srf = view.findViewById(R.id.srf);
        mEmptyView = view.findViewById(R.id.empty_view);
        mWebView.setSwipeRefreshLayout(srf);
        srf.setColorSchemeResources(R.color.main_red, R.color.main_yellow, R.color.main_blue);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mUrl);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KLog.e(mUrl);
        initWebView(mUrl);
    }

    public void loadData() {
        mWebView.loadUrl(mUrl);
    }

    protected void addJavascriptInterface() {
        mWebView.addJavascriptInterface(new RunJavascript(), "myjs");//js调用android函数
    }

    private void initWebView(final String urlStr) {
        addJavascriptInterface();
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);    //支持js
        //        mWebSettings.setBuiltInZoomControls(true);//设置显示缩放按钮
        mWebSettings.setSupportZoom(true);          //支持缩放
        mWebSettings.setUseWideViewPort(true);      //自适应屏幕
        mWebSettings.setLoadWithOverviewMode(true); //自适应屏幕
        mWebView.getSettings().setCacheMode(mWebSettings.LOAD_NO_CACHE);
        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(mWebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //WebViewClient就是帮助WebView处理各种通知、请求事件的
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                KLog.e(url);
                srf.post(new Runnable() {
                    @Override
                    public void run() {
                        srf.setRefreshing(true);
                    }
                });
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                KLog.e(url);
                showLoading(false);
                srf.setRefreshing(false);


                //对图片开启缩放
                //                if (url.contains(".jpg") || url.contains(".png") || url.contains(".JPG") || url.contains(".PNG")) {
                //                    mWebSettings.setBuiltInZoomControls(true);
                //                } else {
                //                    mWebSettings.setBuiltInZoomControls(false);
                //                }

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                view.requestFocus();
                KLog.e(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showLoading(false);
                srf.setRefreshing(false);
                mEmptyView.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                mFailingUrl = failingUrl;
                count++;
                KLog.e("failingUrl:" + failingUrl + "errorCode:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) { //获取网页title
                super.onReceivedTitle(view, title);

            }

            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 80) { // 加载完成
                    showLoading(false);
                    srf.setRefreshing(false);
                }
            }
        });
        mWebView.loadUrl(urlStr);
    }

    @Override
    public void onDestroy() {  //WebView使用完记得释放所有资源
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearFormData();
            mWebView.clearCache(true);
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
            KLog.e("kill....");
        }
        showLoading(false);
        srf.setRefreshing(false);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        showLoading(false);
        srf.setRefreshing(false);
    }

    private void reLoadURL(String reurl) {
        mWebView.reload();
        mWebView.loadUrl(reurl);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {

                    if (NetUtils.isNetConnected(mContext)) {
                        if (count == 1) {
                            mEmptyView.setVisibility(View.GONE);
                            if (mWebView != null) {
                                mWebView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            count = 1;
                        }
                    } else {
                        showToast("请打开wifi链接");
                        count = 1;
                    }
                } catch (Exception e) {

                }

            }
        }, 1000);
    }

    protected class RunJavascript { // js相关
        @JavascriptInterface
        public void runOnAndroidJavaScript(final String result) {
            KLog.e("result:" + result);

        }

        @JavascriptInterface
        public void getResultJavaScript(final String result) {
            KLog.e("result: " + result);
        }
    }
}
