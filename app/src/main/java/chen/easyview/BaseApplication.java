package chen.easyview;

import android.app.Application;

import com.socks.library.KLog;

/**
 * Created by Chen on 2017/1/15.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(true,"CHEN");
    }
}
