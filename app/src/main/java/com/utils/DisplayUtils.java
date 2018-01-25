package com.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.socks.library.KLog;

/**
 * 屏幕分辨率
 */
public class DisplayUtils {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static int SCREEN_DENSITY_DPI;
    public static float SCREEN_DENSITY_DPI_X;
    public static float SCREEN_DENSITY_DPI_Y;
    public static float SCREEN_DENSITY;
    public static float SCREEN_SCALED_DENSITY;
    private static boolean sInitialed;

    public static void init(Context context) {
        if (sInitialed || context == null) {
            return;
        }
        sInitialed = true;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_SCALED_DENSITY = dm.scaledDensity;
        SCREEN_DENSITY_DPI = dm.densityDpi;
        SCREEN_DENSITY_DPI_X = dm.xdpi;
        SCREEN_DENSITY_DPI_Y = dm.ydpi;

        KLog.e("\n" +
                "-----------DisplayUtils-----------\n" +
                "SCREEN_WIDTH_PIXELS   :  "+SCREEN_WIDTH_PIXELS+"\n" +
                "SCREEN_HEIGHT_PIXELS  :  "+SCREEN_HEIGHT_PIXELS+"\n" +
                "SCREEN_DENSITY        :  "+SCREEN_DENSITY+"\n" +
                "SCREEN_SCALED_DENSITY :  "+SCREEN_SCALED_DENSITY+"\n" +
                "SCREEN_DENSITY_DPI    :  "+SCREEN_DENSITY_DPI+"\n" +
                "SCREEN_DENSITY_DPI_X  :  "+SCREEN_DENSITY_DPI_X+"\n" +
                "SCREEN_DENSITY_DPI_Y  :  "+SCREEN_DENSITY_DPI_Y+"\n" +
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

}
