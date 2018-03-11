package chen.easyview.base;

/**
 * Created by chen on 2017/2/17.
 */

public interface BaseConfig {

    boolean isDebug = true;
    boolean isDebugForNetRequest = false && isDebug;
    boolean isDebugForNetResponse = false && isDebug;

    String DIR_NAME = "EasyView";

    String logTag = "EasyView";

    //--------------测试阶段手机的固定宽-------------------------

    int testWidthScreen = 720;
    int testHeightScreen = 1280;

    String SERVER_IP = "http://192.168.1.102:8080";

    String NET_RETROFIT_CACHE = "/netretrofitdatacache";//网络数据存储

    String BASE_URL = "http://www.ztxmw.com:8088/";
}
