package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.socks.library.KLog;
import com.view.CommonProgressDialog;

import chen.easyview.base.BaseApplication;


/**
 * 提示工具类
 */

public class ShowUtils {

    private static Toast toast = null;
    private static CommonProgressDialog progressDialog = null;


    public static synchronized void showProgressDialog(Context context) {
        try {
            if (context == null) {
                return;
            }
            if (progressDialog == null) {
                progressDialog = new CommonProgressDialog(context);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dismissProgressDialog();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
            }
            if (context != null && !((Activity) context).isFinishing() && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            KLog.e("ShowUtils showProgressDialog " + e.getMessage());
        }
    }

    public static synchronized void showProgressDialog(Context context, String content) {
        try {
            if (context == null) {
                return;
            }
            if (progressDialog == null) {
                progressDialog = new CommonProgressDialog(context, content);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dismissProgressDialog();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
            }
            if (progressDialog.getContext() != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            KLog.e("ShowUtils showProgressDialog " + e.getMessage());
        }
    }

    public static void dismissProgressDialog() {
        try {
            if (progressDialog == null) {
                return;
            }
            progressDialog.dismiss();
            progressDialog = null;
        } catch (Exception e) {
            KLog.e("ShowUtils dismissProgressDialog " + e.getMessage());
        }
    }

    public static void showToast(String showText) {
        if (showText == null || "null".equals(showText)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), showText, Toast.LENGTH_LONG);
        } else {
            toast.setText(showText);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }


    //解决循环点击弹出的toast 问题
    private static void way(Context activity, String str) {
        if (toast == null) {
            toast = Toast.makeText(activity, str, Toast.LENGTH_LONG);
        } else {
            toast.setText(str);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

}
