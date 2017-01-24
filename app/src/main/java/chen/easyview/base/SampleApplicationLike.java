package chen.easyview.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.socks.library.KLog;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Chen on 2017/1/23.
 */

@DefaultLifeCycle(application = "chen.easyview.base.BaseApplication2",flags = ShareConstants.TINKER_ENABLE_ALL)
public class SampleApplicationLike extends DefaultApplicationLike {

    private static Context context;

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        TinkerInstaller.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplication();
        KLog.init(true,"CHEN");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("CHEN"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static Context getContext() {
        return context;
    }

}
