package chen.easyview.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    /**
     * 头像管理
     */
    @Multipart
    @POST("BChatApi/UploadImg.ashx")
    Observable<JsonResultBean<String>> uploadIMPic(@QueryMap Map<String, String> params,
                                                   @Part("description") RequestBody description,
                                                   @Part MultipartBody.Part file);

    @GET("Jw/Detail/GetJobDetail.ashx")
    Observable<JsonResultBean<Object>> getJobDetail(@Query("sn") String sn);


    @GET("BChatApi/Resume.ashx")
    Observable<JsonResultBean<Object>> getResumeVars(@Query("action") String action, @Query("userid") String userid, @Query("jobsn") String jobs);

    @GET("BChatApi/Job.ashx")
    Observable<JsonResultBean<Object>> getIMJob(@Query("action") String action, @Query("userid") String userid, @Query("jobsn") String jobsn);











    @FormUrlEncoded
    @POST("app/App/isRegisterOk")
    Observable<JsonResultBean<Boolean>> postIsRegisterOk(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/userLogin")
    Observable<JsonResultBean<String>> postUserLogin(@FieldMap Map<String, String> params);

    //验证码登陆
    @FormUrlEncoded
    @POST("app/App/userLogin2")
    Observable<JsonResultBean<String>> postUserLogin2(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/userRegister")
    Observable<JsonResultBean<String>> postUserRegister(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("comment/comment/getCode")
    Observable<JsonResultBean<String>> postGetCode(@FieldMap Map<String, String> params);

    @GET("comment/comment/codeIsOk")
    Observable<JsonResultBean<Boolean>> getCodeIsOk(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/updateLoginPassword")
    Observable<JsonResultBean<String>> postUpdateLoginPassword(@FieldMap Map<String, String> params);
//
//    @GET("app/App/selectUserInfo")
//    Observable<JsonResultBean<UserInfoBean>> getSelectUserInfo(@QueryMap Map<String, String> params);
//
//
//    @GET("app/App/shoppingMarket")
//    Observable<JsonResultBean<ShoppingMarketBean>> getShoppingMarket();
//
//    @GET("app/App/getGoods")
//    Observable<JsonResultBean<ArrayList<GoodsBean>>> getGoods(@QueryMap Map<String, String> params);
//
//    @GET("app/App/getGoodsSku")
//    Observable<JsonResultBean<GoodsDetailBean>> getGoodsSku(@QueryMap Map<String, String> params);
//
//    @GET("app/App/enterpriseList")
//    Observable<JsonResultBean<ArrayList<EnterpriseBean>>> getEnterpriseList(@QueryMap Map<String, String> params);
//
//    @GET("app/App/GoodsTypes2")
//    Observable<JsonResultBean<GoodsTypeGroupBean>> getGoodsTypes2();
//
//    @GET("app/App/GoodsTypes3")
//    Observable<JsonResultBean<ArrayList<GoodsTypeBean>>> getGoodsTypes3(@QueryMap Map<String, String> params);
//
//    @GET("app/App/enterpriseInfo")
//    Observable<JsonResultBean<EnterpriseBean>> getEnterpriseInfo(@QueryMap Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST("app/App/enterpriseInfo")
//    Observable<JsonResultBean<String>> postEnterpriseFollow(@FieldMap Map<String, String> params);
//
//
//    @GET("app/App/getDongTai")
//    Observable<JsonResultBean<ArrayList<LoveCircleFocusBean>>> getDongTai(@QueryMap Map<String, String> params);
//
//    @GET("app/App/getOneDongTai")
//    Observable<JsonResultBean<LoveCircleFocusBean>> getOneDongTai(@QueryMap Map<String, String> params);
//
//    @GET("app/App/getDongTaiComment")
//    Observable<JsonResultBean<ArrayList<CommentBean>>> getDongTaiComment(@QueryMap Map<String, String> params);
//
//    @GET("app/App/getNews")
//    Observable<JsonResultBean<ArrayList<LoveCircleNewsBean>>> getNews(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/pushCommentToNews")
    Observable<JsonResultBean<String>> postPushCommentToNews(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/pushCommentToDongTai")
    Observable<JsonResultBean<String>> postPushCommentToDongTai(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/deleteOneDongTai")
    Observable<JsonResultBean<String>> postDeleteOneDongTai(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/isDianZanNews")
    Observable<JsonResultBean<String>> postIsDianZanNews(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/isDianZanDongTai")
    Observable<JsonResultBean<String>> postIsDianZanDongTai(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("app/App/pushOneDongTai")
    Observable<JsonResultBean<String>> postPushOneDongTai(@FieldMap Map<String, String> params);

}
