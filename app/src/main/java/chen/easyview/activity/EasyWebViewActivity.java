package chen.easyview.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.BaseActivity;
import com.socks.library.KLog;
import com.utils.NetUtils;
import com.utils.TextUtils;
import com.view.WebViewForRefresh;
import com.view.percentlayout.PercentLinearLayout;

import chen.easyview.R;

/**
 * Created by chen on 2016/10/29.
 */

public class EasyWebViewActivity extends BaseActivity {

    public static final String URL = "EASYWEBVIEW_URL";
    private PercentLinearLayout ll_failedload;
    private String mTitle, mWebTitle, mUrl, mfailingUrl;
    private int count = 0, mAction, mProId, flag = 0;
    private SwipeRefreshLayout srf;
    private WebViewForRefresh mWebView;
    private WebSettings webSettings;
    private Toolbar mToolbar;
    private Handler mHandler;

    @Override
    protected int getContentView() {
        return R.layout.activity_easy_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        srf = findViewById(R.id.srf);
        mToolbar = findViewById(R.id.toolbar);

        mWebView = findViewById(R.id.webview);
        ll_failedload = findViewById(R.id.ll_failedload);
        ll_failedload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e(mfailingUrl);
                reLoadURL(mfailingUrl);
            }
        });
        srf.setColorSchemeResources(R.color.main_red, R.color.main_yellow, R.color.main_blue);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mUrl);
            }
        });
        mWebView.setSwipeRefreshLayout(srf);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        mUrl = getIntent().getStringExtra(URL);
        mTitle = getIntent().getStringExtra("TITLE");
        mAction = getIntent().getIntExtra("ACTION", -1);
        mProId = getIntent().getIntExtra("KEY_PRO_ID", -1);
        if (TextUtils.isValidate(mTitle)) {
            mWebTitle = mTitle; // WebTitle作为动态改变的title的判断依据；
            mToolbar.setTitle(mTitle);
        } else {
            mWebTitle = "";
            mToolbar.setTitle(mWebTitle);
        }
        if (TextUtils.isValidate(mUrl)) {
            KLog.e(mUrl);
            initWebView(mUrl);
        } else {
            KLog.e("NOT URL!");
            initWebView("http://www.baidu.com");
        }
    }

    private void initWebView(final String urlStr) {
        mWebView.addJavascriptInterface(new RunJavascript(), "myjs");//js调用android函数
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);    //支持js
        //        webSettings.setBuiltInZoomControls(true);//设置显示缩放按钮
        webSettings.setSupportZoom(true);          //支持缩放
        webSettings.setUseWideViewPort(true);      //自适应屏幕
        webSettings.setLoadWithOverviewMode(true); //自适应屏幕
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.clearHistory();
        mWebView.clearFormData();
        getCacheDir().delete();
        mWebView.clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
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
                showLoading(false);
                srf.setRefreshing(false);
                KLog.e(url);

                if ("".equals(mWebTitle)) {//按back键时改变title
                    KLog.e(view.getTitle());
                    mToolbar.setTitle(view.getTitle());
                }

                //对图片开启缩放
                //                if (url.contains(".jpg") || url.contains(".png") || url.contains(".JPG") || url.contains(".PNG")) {
                //                    webSettings.setBuiltInZoomControls(true);
                //                } else {
                //                    webSettings.setBuiltInZoomControls(false);
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
                ll_failedload.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                mfailingUrl = failingUrl;
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
                if ("".equals(mWebTitle)) {  //没有从外部传入title
                    KLog.e(title);
                    mToolbar.setTitle(title);
                }
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
    protected void onDestroy() {  //WebView使用完记得释放所有资源
        if (mWebView != null) {
            ViewGroup view = (ViewGroup) getWindow().getDecorView();

            mWebView.clearHistory();
            mWebView.clearFormData();
            getCacheDir().delete();
            view.removeView(mWebView);
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
    protected void onPause() {
        super.onPause();
        showLoading(false);
        srf.setRefreshing(false);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void reLoadURL(String reurl) {
        mWebView.reload();
        mWebView.loadUrl(reurl);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {

                    if (NetUtils.isNetConnected(EasyWebViewActivity.this)) {
                        if (count == 1) {
                            ll_failedload.setVisibility(View.GONE);
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

    class RunJavascript { // js相关
        @JavascriptInterface
        public void runOnAndroidJavaScript(final String result) {
            KLog.e("result:" + result);
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

        @JavascriptInterface
        public void getResultJavaScript(final String result) {
            KLog.e("result: " + result);
        }
    }

}
