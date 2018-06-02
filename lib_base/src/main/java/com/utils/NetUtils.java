package com.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * 网络相关的工具类
 */
public class NetUtils {
    /**
     * cannot be instantiated
     */
    private NetUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 网络管理者
     */
    private static ConnectivityManager connManager;

    /**
     * 获取网络管理者
     */
    private static void getConnManager(Context context) {
        if (connManager == null) {
            connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    /**
     * 判断网络状态
     *
     * @return 0: 无网络， 1:WIFI， 2:其他（流量）
     */
    public static int getNetType(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return 0;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetConnected(Context context) {
        getConnManager(context);
        if (connManager != null) {
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnected(Context context) {
        return getNetType(context) == 1;

    }

    /**
     * 判断当前的GPRS是否打开
     */
    public static boolean switchGprsCloset(Context context) {
        getConnManager(context);
        Class<?> cls = connManager.getClass();
        boolean isGprsConn = isGprsEnabled(context);
        try {
            Method setMethod = cls.getMethod("setMobileDataEnabled", new Class<?>[]{boolean.class});
            setMethod.invoke(connManager, !isGprsConn);
        } catch (Exception e) {
        }
        return !isGprsConn;
    }

    /**
     * 判断当前GPRS是否可用
     */
    public static boolean isGprsEnabled(Context context) {
        getConnManager(context);
        Class<?> cls = connManager.getClass();
        try {
            Method getMethod = cls.getMethod("getMobileDataEnabled");
            return (Boolean) getMethod.invoke(connManager);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 打开网络设置界面
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 直接设置WiFi状态
     */
    public static void setWifiState(Context context, boolean isopen) {
        if (isWifiConnected(context)) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wm.setWifiEnabled(isopen);
        }
    }
}
