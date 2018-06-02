package com.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by chennuo on 2018/3/30.
 */
public class WebViewForRefresh extends WebView {

    private SwipeRefreshLayout swipeRefreshLayout;

    public WebViewForRefresh(Context context) {
        super(context);
    }

    public WebViewForRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewForRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout layout) {
        swipeRefreshLayout = layout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.getScrollY() == 0) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

}
