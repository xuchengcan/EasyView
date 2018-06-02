package chen.login.api;

import com.net.JsonResultBean;

import java.util.Map;

import chen.login.bean.LoginBean;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    /**
     * 登录
     *
     * @param phone    手机号码
     * @param password 密码
     */
    @GET("/api/v1/auth/login")
    Observable<JsonResultBean<LoginBean>> getLogin(
            @Query("phone") String phone,
            @Query("password") String password);

}
