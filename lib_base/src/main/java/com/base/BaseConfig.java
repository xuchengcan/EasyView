package com.base;

/**
 * 所有配置相关的基础类
 */
public interface BaseConfig {

    boolean isDebug = true;
    boolean isDebugForDisplay = true && isDebug;
    boolean isDebugForNetRequest = true && isDebug;
    boolean isDebugForNetResponse = true && isDebug;


    String NET_RETROFIT_CACHE = "/NET_RETROFIT_CACHE";//网络数据存储

    String BASE_DEBUG_URL = "https://api.xhuijia.com/";
//    String BASE_DEBUG_URL = "https://testapi.xhuijia.com/";

    String SECRET_KRY = "school";

    String DIR_NAME = "EasyView";

    String logTag = "EasyView";


    String SERVER_IP = "http://192.168.1.102:8080";

    String BASE_URL = "http://www.klxab.com:8088/";
}
