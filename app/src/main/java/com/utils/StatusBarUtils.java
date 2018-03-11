package com.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import chen.easyview.R;

/**
 * Created by chennuo on 2018/2/28.
 * 参考 http://blog.csdn.net/u011555564/article/details/69055126?utm_source=itdadao&utm_medium=referral
 */

public class StatusBarUtils {

    public static final int StatusBarColor = Color.parseColor("#8a281d");
    //    public static final long StatusBarColor4Alpha = Long.parseLong("8a281d", 16);
    //    public static final long MainRedColor4Alpha = Long.parseLong("e63d2e", 16);
    @Deprecated
    SystemBarTintManager tintManager;

    public static int getStatusBarColor4Alpha(int alpha) {
        return getColor4Alpha(alpha, Long.parseLong("8a281d", 16));
    }

    public static int getMainRedColor4Alpha(int alpha) {
        return getColor4Alpha(alpha, Long.parseLong("e63d2e", 16));
    }

    public static int getColor4Alpha(int alpha, long color) {
        return (int) ((alpha << 24) | color);
    }

    /**
     * 设置透明状态栏
     *
     * @param activity 需要设置的activity
     * @param on       是否设置
     */
    @TargetApi(19)
    @Deprecated
    public static void setTranslucentStatus(Activity activity, boolean on) {
        if (on) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 将activity中的activity中的状态栏设置为一个纯色
     *
     * @param activity 需要设置的activity
     * @param color    设置的颜色（一般是titlebar的颜色）
     */
    public static void setColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //            //5.0及以上，不设置透明状态栏，设置会有半透明阴影
            //            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //            //设置statusBar的背景色
            //            activity.getWindow().setStatusBarColor(color);
            //            setRootView(activity);
            setColor4LOLLIPOP(activity, color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //            // 生成一个状态栏大小的矩形
            //            View statusView = createStatusBarView(activity, color);
            //            // 添加 statusView 到布局中
            //            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            //            decorView.addView(statusView);
            //            //让我们的activity_main。xml中的布局适应屏幕
            //            setRootView(activity);
            setColor4KITKAT(activity, color);
        }
    }

    @TargetApi(19)
    public static void setColor4KITKAT(Activity activity, @ColorInt int color) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 生成一个状态栏大小的矩形
        View statusView = createStatusBarView(activity, color);
        // 添加 statusView 到布局中
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(statusView);
        //让我们的activity_main。xml中的布局适应屏幕
        setRootView(activity);
    }

    @TargetApi(21)
    public static void setColor4LOLLIPOP(Activity activity, @ColorInt int color) {
        //5.0及以上，不设置透明状态栏，设置会有半透明阴影
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置statusBar的背景色
        activity.getWindow().setStatusBarColor(color);
        setRootView(activity);
    }

    /**
     * 设置根布局参数，让跟布局参数适应透明状态栏
     */
    public static void setRootView(Activity activity) {
        //获取到activity_main.xml文件
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

        //如果不是设置参数，会使内容显示到状态栏上
        rootView.setFitsSystemWindows(true);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏的高度
     *
     * @param context
     * @return
     */
    private static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * 框架使用的沉浸式,这个比较好,能在布局里面少些代码
     */
    @TargetApi(19)
    protected void setImmersive() {
        setTranslucentStatus(true);
        tintManager.setStatusBarTintResource(R.color.translucent);
    }

    @TargetApi(19)
    protected void setStatusBarTintResource(int res) {
        setTranslucentStatus(true);
        //为状态栏着色
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(res);
    }

    @TargetApi(19)
    protected void setStatusBarTintColor(int color) {
        setTranslucentStatus(true);
        //为状态栏着色
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    @TargetApi(19)
    protected void setStatusBarTintDrawable(Drawable drawable) {
        setTranslucentStatus(true);
        //为状态栏着色
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintDrawable(drawable);
    }

    @TargetApi(19)
    @Deprecated
    protected void setTranslucentStatus(boolean on) {
        //        tintManager = new SystemBarTintManager(this);
        //        Window win = getWindow();
        //        WindowManager.LayoutParams winParams = win.getAttributes();
        //        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //        if (on) {
        //            winParams.flags |= bits;
        //        } else {
        //            winParams.flags &= ~bits;
        //        }
        //        win.setAttributes(winParams);
    }
}
