package com.net;

import android.app.Application;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.util.Log;

import com.base.BaseConfig;
import com.socks.library.KLog;
import com.utils.NetUtils;
import com.utils.ShowUtils;
import com.utils.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import chen.lib_base.BuildConfig;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static RetrofitUtils instance;

    public static final long INVALIDATE_STALE_CACHE = 60 * 60 * 24 * 2;  //设缓存有效期为两天

    private OkHttpClient client;

    private Retrofit retrofit;

    /**
     * 获取Retrofit管理对象(单例)
     */
    public static RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }

    public void init(Application application){
        retrofit = new Retrofit.Builder()
//                .baseUrl(BaseConfig.isDebug?BaseConfig.BASE_DEBUG_URL:BaseConfig.BASE_URL)
                .baseUrl(BuildConfig.DEFAULT_HOSTS_URL)
                .client(getClient(application))
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                .build();
    }

    public <T> T getService(Class<T> cls) {
        return retrofit.create(cls);
    }

    private OkHttpClient getClient(Application application) {
        if (client == null) {

            //设置缓存
            File cacheFile = new File(application.getApplicationContext().getCacheDir(), BaseConfig.NET_RETROFIT_CACHE);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //10Mb


            //addinterceptor添加的是aplication拦截器，他只会在response被调用一次。
            //addNetworkInterceptor添加的是网络拦截器，他会在在request和resposne是分别被调用一次，
            Interceptor app_interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();//获取请求
                    //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
                    if (NetUtils.getNetType(application.getApplicationContext()) == 0) {
                        request = request.newBuilder()
                                //这个的话内容有点多啊，大家记住这么写就是只从缓存取，想要了解这个东西我等下在
                                // 给大家写连接吧。大家可以去看下，获取大家去找拦截器资料的时候就可以看到这个方面的东西反正也就是缓存策略。
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                        KLog.e("应用拦截器   ：：  request no network");
//                        if (BaseConfig.isDebug) {
//                            logForAppNotNetWorkRequest(request);
//                        }
                    }
                    Response response = chain.proceed(request);
                    if (NetUtils.getNetType(application.getApplicationContext()) != 0) {
                        return response.newBuilder()
                                //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                                .addHeader("Cache-Control", "public, max-age=" + 0)
                                .addHeader("app-version", "Android/" + BuildConfig.VERSION_NAME)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        int maxTime = 60;
                        KLog.e("应用拦截器   ：：  request no network ");
                        return response.newBuilder()
                                //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                                .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                                .removeHeader("Pragma")
                                .build();
                    }

                }
            };
            Interceptor network_interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();//获取请求
                    //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
                    if (NetUtils.getNetType(application.getApplicationContext()) == 0) {
                        request = request.newBuilder()
                                //这个的话内容有点多啊，大家记住这么写就是只从缓存取，想要了解这个东西我等下在
                                // 给大家写连接吧。大家可以去看下，获取大家去找拦截器资料的时候就可以看到这个方面的东西反正也就是缓存策略。
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                        KLog.e("网络拦截器   ：：  response no network");
                    }
//                    if (BaseConfig.isDebugForNetRequest) {
//                        logForNetRequest(request);
//                    }
                    Response response = chain.proceed(request);

                    if (NetUtils.getNetType(application.getApplicationContext()) != 0) {
//                        if (BaseConfig.isDebugForNetResponse) {
////                            return logForNetResponse(response);
//                        }
                        return response.newBuilder()
                                //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                                .addHeader("Cache-Control", "public, max-age=" + 0)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        int maxTime = 60;
                        KLog.e("网络拦截器   ：：  response no network ");
                        return response.newBuilder()
                                //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                                .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                                .removeHeader("Pragma")
                                .build();
                    }
                }
            };

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(app_interceptor)//应用程序拦截器
                    .addNetworkInterceptor(network_interceptor)//网络拦截器
                    .retryOnConnectionFailure(true)//连接失败后是否重新连接
                    .connectTimeout(10, TimeUnit.SECONDS)//连接超时时间10S
                    .readTimeout(10, TimeUnit.SECONDS)//读取超时时间10s
                    .writeTimeout(10, TimeUnit.SECONDS)//读取超时时间10s
                    .build();
        }
        return client;
    }

    public static <T> boolean isSuccessStatusCode(JsonResultBean<T> result) {
        if (result == null) {
            KLog.e("result is null");
            return false;
        }
        if (result.code != null) {
            if (result.code.equals("200")) {
                return true;
            } else {
                KLog.e(getFailCodeMessage(result.code));
                return false;
            }
        } else {
            KLog.e("code is null");
            return false;
        }
    }

    public static <T> boolean isSuccessData(JsonResultBean<T> result) {
        if (result == null) {
            KLog.e("result is null");
            return false;
        }
        if (result.data != null) {
            return true;
        } else {
            KLog.e("data is null");
            return false;
        }
    }

    public static void showErrorToast() {
        ShowUtils.dismissProgressDialog();
        ShowUtils.showToast("请求失败，请重试");
    }

    public static void reportError(Throwable e) {
        ShowUtils.dismissProgressDialog();
        if (e != null) {
            e.printStackTrace();
            if (TextUtils.isValidate(e.getMessage())) {
                KLog.e(e.getMessage());
                if (e.getMessage().contains("timeout")) {
                    ShowUtils.showToast("请求超时，请重试");
                } else if (e.getMessage().contains("Failed to connect")) {
                    ShowUtils.showToast("无法连接服务器");
                } else {
                    ShowUtils.showToast("网络错误，请重试");
                }
            }
        }
    }

    public static String getFailCodeMessage(String code) {
        switch (code) {
            case "201":
                return "服务端发生异常";
            case "100":
                return "缺少sign参数";
            case "101":
                return "缺少timestamp参数";
            case "102":
                return "访问已过期";
            case "103":
                return "没有访问权限";
            default:
                return "未知错误码：" + code;
        }
    }

//    public static <T> void checkData(JsonResultBean<T> resultBean, checkDataListener<T> listener) {
//        if (resultBean == null) {
//            listener.showErrorMessage("resultBean is null");
//        }
//        try {
//            if (resultBean.code != null) {
//                if (resultBean.code.equals("200")) {
//                    listener.getData(resultBean.data);
//                    if (TextUtils.isValidate(resultBean.message)) {
//                        listener.showMessage(resultBean.message);
//                    }
//                } else {
//                    listener.showErrorMessage(getFailCodeMessage(resultBean.code));
//                    if (TextUtils.isValidate(resultBean.message)) {
//                        listener.showErrorMessage(resultBean.message);
//                    }
//                }
//            } else {
//                listener.showErrorMessage("code is null");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            listener.showErrorMessage("checkData error");
//        }
//
//    }

    private static void logForAppNotNetWorkRequest(Request request) {
        try {
            String e = request.url().toString();
            KLog.i("========Request\'Log=======");
            KLog.i("method  : " + request.method());
            KLog.i("url     : " + e);
            Headers headers = request.headers();
            if (headers != null && headers.size() > 0) {
                KLog.i("\n" +
                        "-----------------------headers-----------------------\n" +
                        headers.toString() +
                        "-----------------------------------------------------");
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    KLog.i("RequestBody\'s contentType  : " + mediaType.toString());
                    if (isText(mediaType)) {
                        KLog.i("RequestBody\'s content      : " + bodyToString(request));
                    } else {
                        KLog.i("RequestBody\'s content      :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            String cacheControl = request.cacheControl().toString();
            if (cacheControl != null) {
                KLog.i("RequestBody\'s cacheControl : " + cacheControl);
            }
            if ("POST".equals(request.method())) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    KLog.i("RequestBody\'s params       :{" + sb.toString() + "}");
                }
            }
            KLog.i("===========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void logForNetRequest(Request request) {
        try {
            String e = request.url().toString();
            KLog.i("========Request\'Log=======");
            KLog.i("method  : " + request.method());
            KLog.i("url     : " + e);
            Headers headers = request.headers();
            if (headers != null && headers.size() > 0) {
                KLog.i("\n" +
                        "-----------------------headers-----------------------\n" +
                        headers.toString() +
                        "-----------------------------------------------------");
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    KLog.i("RequestBody\'s contentType  : " + mediaType.toString());
                    if (isText(mediaType)) {
                        KLog.i("RequestBody\'s content      : " + bodyToString(request));
                    } else {
                        KLog.i("RequestBody\'s content      :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            String cacheControl = request.cacheControl().toString();
            if (cacheControl != null) {
                KLog.i("RequestBody\'s cacheControl : " + cacheControl);
            }
            if ("POST".equals(request.method())) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    KLog.json("{" + sb.toString() + "}");
                }
            }
            KLog.i("===========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Response logForNetResponse(Response response) {
        try {
            KLog.i("========Response\'Log=======");
            Response.Builder e = response.newBuilder();
            Response clone = e.build();
            KLog.i("url         : " + clone.request().url());
            KLog.i("code        : " + clone.code());
            KLog.i("protocol    : " + clone.protocol());
            if (!android.text.TextUtils.isEmpty(clone.message())) {
                KLog.e("message     : " + clone.message());
            }

            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    KLog.i("contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        KLog.e(resp);
                        body = ResponseBody.create(mediaType, resp);
                        KLog.i("===========================");
                        return response.newBuilder()
                                //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                                .addHeader("Cache-Control", "public, max-age=" + 0)
                                .body(body)
                                .removeHeader("Pragma")
                                .build();

                    }
                    KLog.i("content     :  maybe [file part] , too large too print , ignored!");
                }
            }
            KLog.i("===========================");
        } catch (Exception var7) {
        }
        return response.newBuilder()
                //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                .addHeader("Cache-Control", "public, max-age=" + 0)
                .removeHeader("Pragma")
                .build();

    }

    private static boolean isText(MediaType mediaType) {
        return mediaType.type() != null && mediaType.type().equals("text") ? true : mediaType.subtype() != null && (mediaType.subtype().equals("json") || mediaType.subtype().equals("xml") || mediaType.subtype().equals("html") || mediaType.subtype().equals("webviewhtml"));
    }

    private static String bodyToString(Request request) {
        try {
            Request e = request.newBuilder().build();
            Buffer buffer = new Buffer();
            e.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException var4) {
            return "something error when show requestBody.";
        }
    }

    public interface checkDataListener<T> {
        void showMessage(String msg);

        void getData(T data);

        void showErrorMessage(String msg);
    }
}
