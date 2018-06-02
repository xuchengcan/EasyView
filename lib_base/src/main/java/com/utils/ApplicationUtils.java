package com.utils;

import android.app.Application;

/**
 * Created by chennuo on 2018/5/31.
 */
public class ApplicationUtils {

    private static Application mApplication;

    private static final ApplicationUtils ourInstance = new ApplicationUtils();

    static ApplicationUtils getInstance() {
        return ourInstance;
    }

    private ApplicationUtils() {
    }

    public static void init(Application application){
        mApplication = application;
    }

    public Application getApplication(){
        return mApplication;
    }
}
