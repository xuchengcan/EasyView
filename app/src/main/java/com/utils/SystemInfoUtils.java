package com.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.socks.library.KLog;

import chen.easyview.base.BaseApplication;

public class SystemInfoUtils {

    /**
     * 获取版本号
     * int
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = BaseApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getContext().getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取版本号
     * String
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager manager = BaseApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getContext().getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获本机手机号码
     */
    public static String getLocalPhoneNum() {
        String mNumber = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getContext().getSystemService(
                    Context.TELEPHONY_SERVICE);// 电话状态
            mNumber = telephonyManager.getLine1Number();
        } catch (Exception e) {
            mNumber = "";
            KLog.e("" + e.getMessage());
        } finally {
            KLog.i("" + mNumber);
        }

        return mNumber == null ? "" : mNumber;
    }

    /**
     * 获取设备的 IMEI 号
     */
    public static String getImeiID() {
        String imeiID = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getContext().getSystemService(
                    Context.TELEPHONY_SERVICE);// 电话状态
            imeiID = telephonyManager.getDeviceId();
        } catch (Exception e) {
            imeiID = "";
        }

        return imeiID == null ? "" : imeiID;
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

}
