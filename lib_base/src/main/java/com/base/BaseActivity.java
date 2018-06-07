package com.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.socks.library.KLog;
import com.utils.ShowUtils;
import com.utils.StatusBarBlackUtils;
import com.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2017/2/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isImmersive = true;
    protected boolean isBindEventBusHere = false;

    /**
     * 填充布局
     */
    @LayoutRes
    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//屏幕竖屏
        if (isImmersive) {
            StatusBarUtils.setColor(this, StatusBarUtils.StatusBarColor);
            StatusBarBlackUtils.setStatusBarBlackColor(this, true);
        }
        if (isBindEventBusHere) {
            EventBus.getDefault().register(this); //注册事件
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(BaseActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(BaseActivity.this);
    }

    @Override
    protected void onStop() {
        if (getCurrentFocus() != null) {
            KLog.e();
            IBinder iBinder = getCurrentFocus().getWindowToken();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(iBinder, 0);
        }
        showLoading(false);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (isBindEventBusHere) {
            EventBus.getDefault().unregister(this); //取消注册事件
        }
        super.onDestroy();
    }

    protected void showToast(String content) {
        ShowUtils.showToast(content);
    }

    /**
     * 显示是否正在加载中
     */
    protected void showLoading(boolean toggle) {
        if (toggle) {
            ShowUtils.showProgressDialog(this);
        } else {
            ShowUtils.dismissProgressDialog();
        }
    }

    /**
     * 状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //重写了getResources(),使得字体不受系统字体设置的影响：BaseActivity,Chatuidemo的BaseActivity,MainActivity。如以后需要在app中增加调节自体的功能，请参考
    //    public void initFontScale() {
    //        Configuration configuration = getResources().getConfiguration();
    //        configuration.fontScale = (float) 1;
    //        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
    //        DisplayMetrics metrics = new DisplayMetrics();
    //        getWindowManager().getDefaultDisplay().getMetrics(metrics);
    //        metrics.scaledDensity = configuration.fontScale * metrics.density;
    //        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    //    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 测试当前摄像头能否被使用
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }

}
