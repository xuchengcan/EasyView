package com.net;

import com.base.BaseApplication;
import com.base.BaseConfig;
import com.utils.NetUtils;
import com.utils.SharedPreferencesUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by chennuo on 2018/2/5.
 */

public class SignUtils {

    public static Map<String, String> SignParameter(Map<String, String> map) {
        if (BaseConfig.isDebug) {
            return SignParameterForCache(map, map.hashCode() + "");
        }
        Long timeLONG=System.currentTimeMillis()/1000;
        map.put("timestamp", timeLONG + "");
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);//排序
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=").append(map.get(key)).append("&");//拼接字符串
        }
        String linkString = sb.toString();
        linkString = linkString.substring(0, linkString.length() - 1);//去除最后一个'&'
        String signValue = encodeMD5(linkString + BaseConfig.SECRET_KRY);//混合密钥md5
        map.put("sign", signValue);
        return map;
    }

    public static Map<String, String> SignParameterForCache(Map<String, String> map, String cacheName) {
        Long timeLONG;
        if (NetUtils.getNetType(BaseApplication.getContext()) == 0) {
            timeLONG = SharedPreferencesUtils.getLong(cacheName);
        } else {
            timeLONG = System.currentTimeMillis() / 1000;
            SharedPreferencesUtils.put(cacheName, timeLONG);
        }
        map.put("timestamp", timeLONG + "");
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);//排序
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=").append(map.get(key)).append("&");//拼接字符串
        }
        String linkString = sb.toString();
        linkString = linkString.substring(0, linkString.length() - 1);//去除最后一个'&'
        String signValue = encodeMD5(linkString + BaseConfig.SECRET_KRY);//混合密钥md5
        map.put("sign", signValue);
        return map;
    }

    public static String encodeMD5(String s) {
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(s.getBytes("UTF-8"));
            byte[] messageDigest = e.digest();
            return toHexString(messageDigest);
        } catch (Exception var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public static String toHexString(byte[] keyData) {
        if(keyData == null) {
            return null;
        } else {
            int expectedStringLen = keyData.length * 2;
            StringBuilder sb = new StringBuilder(expectedStringLen);

            for(int i = 0; i < keyData.length; ++i) {
                String hexStr = Integer.toString(keyData[i] & 255, 16);
                if(hexStr.length() == 1) {
                    hexStr = "0" + hexStr;
                }

                sb.append(hexStr);
            }

            return sb.toString();
        }
    }

}
