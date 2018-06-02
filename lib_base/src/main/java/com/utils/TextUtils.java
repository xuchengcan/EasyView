package com.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 数据检查和获取帮助类
 */
public class TextUtils {

    public static boolean isValidate(String content) {
        return content != null && !"".equals(content.trim()) && !"null".equals(content.trim());
    }

    public static boolean isValidate(String s,String s2) {
        return isValidate(s) && isValidate(s2);
    }

    public static boolean isValidate(Collection<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isValidate(Map<?, ?> map) {
        return map != null && map.size() > 0;

    }
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }
}
