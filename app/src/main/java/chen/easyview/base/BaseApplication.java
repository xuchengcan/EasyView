package chen.easyview.base;

import android.app.Application;

import com.socks.library.KLog;

/**
 * Created by Chen on 2017/1/15.
 */

//已不使用
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(true,"CHEN");
    }
}
