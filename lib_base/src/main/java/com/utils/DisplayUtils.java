package com.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.socks.library.KLog;

/**
 * 屏幕分辨率
 */
public class DisplayUtils {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static int SCREEN_REAL_WIDTH_PIXELS;
    public static int SCREEN_REAL_HEIGHT_PIXELS;
    public static int SCREEN_DENSITY_DPI;
    public static float SCREEN_DENSITY_DPI_X;
    public static float SCREEN_DENSITY_DPI_Y;
    public static float SCREEN_DENSITY;
    public static float SCREEN_SCALED_DENSITY;
    public static float STATUS_BAR_HEIGHT;
    public static float NAVIGATION_BAR_HEIGHT;
    private static boolean sInitialed;

    public static void init(Context context) {
        if (sInitialed || context == null) {
            return;
        }
        sInitialed = true;
        DisplayMetrics dm = new DisplayMetrics();
        DisplayMetrics rdm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        wm.getDefaultDisplay().getRealMetrics(rdm);
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_REAL_WIDTH_PIXELS = rdm.widthPixels;
        SCREEN_REAL_HEIGHT_PIXELS = rdm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_SCALED_DENSITY = dm.scaledDensity;
        SCREEN_DENSITY_DPI = dm.densityDpi;
        SCREEN_DENSITY_DPI_X = dm.xdpi;
        SCREEN_DENSITY_DPI_Y = dm.ydpi;
        STATUS_BAR_HEIGHT = getStatusBarHeight(context);
        NAVIGATION_BAR_HEIGHT = getNavigationBarHeight(context);

        KLog.e("\n" +
                "-----------DisplayUtils-----------\n" +
                "SCREEN_WIDTH_PIXELS       :  " + SCREEN_WIDTH_PIXELS + "\n" +
                "SCREEN_HEIGHT_PIXELS      :  " + SCREEN_HEIGHT_PIXELS + "\n" +
                "SCREEN_REAL_WIDTH_PIXELS  :  " + SCREEN_REAL_WIDTH_PIXELS + "\n" +
                "SCREEN_REAL_HEIGHT_PIXELS :  " + SCREEN_REAL_HEIGHT_PIXELS + "\n" +
                "SCREEN_DENSITY            :  " + SCREEN_DENSITY + "\n" +
                "SCREEN_SCALED_DENSITY     :  " + SCREEN_SCALED_DENSITY + "\n" +
                "SCREEN_DENSITY_DPI        :  " + SCREEN_DENSITY_DPI + "\n" +
                "SCREEN_DENSITY_DPI_X      :  " + SCREEN_DENSITY_DPI_X + "\n" +
                "SCREEN_DENSITY_DPI_Y      :  " + SCREEN_DENSITY_DPI_Y + "\n" +
                "STATUS_BAR_HEIGHT         :  " + STATUS_BAR_HEIGHT + "\n" +
                "NAVIGATION_BAR_HEIGHT     :  " + NAVIGATION_BAR_HEIGHT + "\n" +
                "----------------------------------");
    }

    public static int dp2px(float dp) {
        final float density = SCREEN_DENSITY;
        return (int) (dp * density + 0.5f);
    }

    public static int px2dp(float px) {
        final float density = SCREEN_DENSITY;
        return (int) (px / density + 0.5f);
    }

    public static int sp2Px(float sp) {
        final float scaledDensity = SCREEN_SCALED_DENSITY;
        return (int) (sp * scaledDensity + 0.5f);
    }

    public static float dpToPx(float dp) {
        final float density = SCREEN_DENSITY;
        return dp * density;
    }

    public static float pxToDp(float px) {
        final float density = SCREEN_DENSITY;
        return px / density;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
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
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void demo(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                //                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 全屏模式，布局延伸至状态栏
                //                View.SYSTEM_UI_FLAG_FULLSCREEN //隐藏状态栏
                //                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 布局延伸至导航栏
                //                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                //                View.SYSTEM_UI_FLAG_LAYOUT_STABLE //保持布局稳定
                //                View.SYSTEM_UI_FLAG_IMMERSIVE // 沉浸式模式
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // 沉浸式模式，有粘黏效果
        );
    }
}
