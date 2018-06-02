package com.net;

import com.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chennuo on 2018/2/28.
 */

public class ParamUtils {

    Map<String, String> mMap;

    public ParamUtils() {
        mMap = new HashMap<>();
    }

    public static ParamUtils build() {
        return new ParamUtils();
    }

    public ParamUtils getMap(Map<String, String> map) {
        mMap = map;
        return this;
    }

    public ParamUtils put(String v, String k) {
        if (TextUtils.isValidate(k)) {
            mMap.put(v, k);
        }
        return this;
    }

    public Map<String, String> signParameter() {
        return SignUtils.SignParameter(mMap);
    }

    public Map<String, String> signParameterForCache(String cacheName) {
        return SignUtils.SignParameterForCache(mMap, cacheName);
    }

}
