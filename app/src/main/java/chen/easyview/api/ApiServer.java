package chen.easyview.api;

import com.net.JsonResultBean;

import java.util.Map;

import chen.easyview.bean.UpdateBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by lindan on 18-2-7.
 * 家长模块相关接口API
 */
public interface ApiServer {

    @GET("app/Project/apkIsLastNew")
    Observable<JsonResultBean<UpdateBean>> getApkIsLastNew(@QueryMap Map<String, String> params);
}