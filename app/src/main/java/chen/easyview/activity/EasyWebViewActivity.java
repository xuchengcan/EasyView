package chen.easyview.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.socks.library.KLog;

import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import chen.easyview.utils.KeyboardUtils;
import chen.easyview.utils.NetUtils;
import chen.easyview.utils.TextUtil;

/**
 * Created by chen on 2017/2/17.
 * 一般需传入URL，TITLE 两个值，仅传URL时，TITLE从网页上获取
 * mIntent.putExtra(EasyWebViewActivity.URL, URL);
 * mIntent.putExtra("TITLE", "title");
 */

public class EasyWebViewActivity extends BaseActivity {

    public static final String URL = "EASYWEBVIEW_URL";

    private LinearLayout ll_failedload;
    private String mTitle, mWebTitle, mUrl, mfailingUrl;
    private int count = 0;
    private WebView mWebView;
    private WebSettings webSettings;
    private Handler mHandler;
    private Toolbar mToolbar;
    private EditText input;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_webview);
        KLog.i();
        initView();
        initData();
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getVisibility() == View.VISIBLE) {
                    input.setVisibility(View.GONE);
                    KeyboardUtils.closeKeyboard(input);
                } else {
                    input.setVisibility(View.VISIBLE);
                    input.setText(mWebTitle);
                    input.setSelection(input.length());
                    KeyboardUtils.openKeyboard(EasyWebViewActivity.this,input);
                }
            }
        });

        ll_failedload = (LinearLayout) findViewById(R.id.ll_failedload);
        ll_failedload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e(mfailingUrl);
                reLoadURL(mfailingUrl);
            }
        });

        input = (EditText) findViewById(R.id.input);
        input.setImeOptions(EditorInfo.IME_ACTION_GO);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    mWebView.loadUrl(input.getText().toString());
                    input.setVisibility(View.GONE);
                    ll_failedload.setVisibility(View.GONE);
                    KeyboardUtils.closeKeyboard(input);
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        mUrl = getIntent().getStringExtra(URL);
        mTitle = getIntent().getStringExtra("TITLE");
        if (TextUtil.isValidate(mTitle)) {
            mWebTitle = mTitle; // WebTitle作为动态改变的title的判断依据；
            mToolbar.setTitle(mTitle);
        } else {
            mWebTitle = "";
            mToolbar.setTitle(mTitle);
        }
        if (TextUtil.isValidate(mUrl)) {
            KLog.e(mUrl);
            initWebView(mUrl);
        } else {
            KLog.e("NOT URL!");
            showToast("未获取URL，默认打开百度");
            mUrl = "https://www.baidu.com";
            initWebView(mUrl);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(final String urlStr) {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.addJavascriptInterface(new RunJavascript(), "myjs");//js调用android函数
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);    //支持js
//        webSettings.setBuiltInZoomControls(true);//设置显示缩放按钮
        webSettings.setSupportZoom(true);          //支持缩放
        webSettings.setUseWideViewPort(true);      //自适应屏幕
        webSettings.setLoadWithOverviewMode(true); //自适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //WebViewClient就是帮助WebView处理各种通知、请求事件的
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                KLog.e(url);
                toggleShowLoading(true);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                toggleShowLoading(false);
                KLog.e(url);
                if ("".equals(mWebTitle)) {//按back键时改变title
                    KLog.e(view.getTitle());
                    mToolbar.setTitle(view.getTitle());
                }
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
                toggleShowLoading(false);
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
                    mToolbar.setTitle(view.getTitle());
                }
            }

            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 80) { // 加载完成
                    KLog.e(newProgress);
                    toggleShowLoading(false);
                }
            }
        });
        mWebView.loadUrl(urlStr);
    }

    @Override
    protected void onDestroy() {  //WebView使用完记得释放所有资源
        if (mWebView != null) {
            ViewGroup view = (ViewGroup) getWindow().getDecorView();
            view.removeView(mWebView);
            mWebView.clearCache(true);
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        KeyboardUtils.closeKeyboard(input);
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void reLoadURL(String reurl) {
        mWebView.loadUrl(reurl);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (NetUtils.isNetConnected(EasyWebViewActivity.this)) {
                    if (count == 1) {
                        ll_failedload.setVisibility(View.GONE);
                        mWebView.setVisibility(View.VISIBLE);
                    } else {
                        count = 1;
                    }
                } else {
                    showToast("请打开wifi链接");
                    count = 1;
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
                    String[] split = result.split(":");

                }
            });
        }

        @JavascriptInterface
        public void getResultJavaScript(final String result) {
            KLog.e("result: " + result);

        }
    }

    private void toggleShowLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
