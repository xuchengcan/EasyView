package chen.easyview.base;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.greenrobot.greendao.database.Database;

import java.util.concurrent.TimeUnit;

import chen.easyview.greendao.DaoMaster;
import chen.easyview.greendao.DaoSession;
import okhttp3.OkHttpClient;

/**
 * Created by Chen on 2017/1/23.
 */


public class BaseApplication extends Application {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;//数据库加密flag

    private static Context context;

    private static DaoSession daoSession;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        KLog.init(true,"CHEN");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("CHEN"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, ENCRYPTED ? "easyview-db-encrypted" : "easyview-db");
        //数据库密码super-secret
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public static Context getContext() {
        return context;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

}
