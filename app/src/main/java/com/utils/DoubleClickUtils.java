package com.utils;

/**
 * Created by chennuo on 2018/1/25.
 */

public class DoubleClickUtils {

    private static long lastClickTime;

    /**
     * 是否发生快速点击事件，防止重发事件产生
     * <p>
     * 设置时间1秒
     */
    public static boolean isDoubleClick() {
        if (isDoubleClick(1000)) {
            return true;
        }
        return false;
    }

    public static boolean isDoubleClick(int time) {
        long curClickTime = System.currentTimeMillis();
        long timeSpan = curClickTime - lastClickTime;
        lastClickTime = curClickTime;
        if (timeSpan < time) {
            return true;
        }
        return false;
    }

}
