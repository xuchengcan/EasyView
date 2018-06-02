package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.base.BaseApplication;
import com.socks.library.KLog;
import com.view.CommonProgressDialog;


/**
 * 提示工具类
 */

public class ShowUtils {

    private static CommonProgressDialog progressDialog = null;
    private static Toast mToast = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

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

    /**
     * Get toast
     */
    private static Toast getToast() {
        if (null != mToast) {
            return mToast;
        }
        return Toast.makeText(BaseApplication.getContext(), "", Toast.LENGTH_SHORT);
    }

    /**
     * Show toast
     *
     * @param msg
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    private static void makeToast(String msg, int duration, int gravity, int xOffset, int yOffset) {
        mToast = getToast();
        mToast.setGravity(gravity, xOffset, (yOffset == Integer.MAX_VALUE ? mToast.getYOffset() : yOffset));
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void showToast(final String msg) {
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                makeToast(msg, Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, Integer.MAX_VALUE);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        makeToast(msg, Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, Integer.MAX_VALUE);
                    }
                });
            }
        } catch (Exception e) {
            KLog.e("ShowUtils showToast " + e.getMessage());
        }
    }
}
