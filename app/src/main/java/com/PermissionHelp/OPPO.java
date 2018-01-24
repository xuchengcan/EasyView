package com.PermissionHelp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * support:
 * 1. oppo a57 android 6.0.1/coloros3.0
 * <p>
 * manager home page, permissions manage page does not work!!!, or
 * {@link Protogenesis#settingIntent()}
 * <p>
 * Created by joker on 2017/8/4.
 */

public class OPPO implements PermissionsPage {
    private final Activity context;
    private final String PKG_3 = "com.coloros.safecenter";
    private final String MANAGER_OUT_CLS_3 = "com.coloros.privacypermissionsentry.PermissionTopActivity";
//    private final String MANAGER_OUT_CLS = "com.coloros.safecenter.permission.singlepage.PermissionSinglePageActivity";

    private final String PKG_OLD = "com.color.safecenter";
    private final String MANAGER_OUT_CLS_OLD = "com.color.safecenter.permission.PermissionManagerActivity";

    public OPPO(Activity context) {
        this.context = context;
    }

    private static String getSystemProperty() {
        String line = "";
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.build.version.opporom");
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    @Override
    public Intent settingIntent() throws Exception {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PACK_TAG, context.getPackageName());
        String OPPO_VERSION = getSystemProperty();
        KLog.e(OPPO_VERSION);
        if (OPPO_VERSION.equals("V3.0")){

            ComponentName comp = new ComponentName(PKG_3, MANAGER_OUT_CLS_3);
            intent.setComponent(comp);
        }else {
            ComponentName comp = new ComponentName(PKG_OLD, MANAGER_OUT_CLS_OLD);
            intent.setComponent(comp);
        }
        return intent;
    }
}
