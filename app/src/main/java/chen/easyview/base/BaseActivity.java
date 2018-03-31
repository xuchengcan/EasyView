package chen.easyview.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.socks.library.KLog;
import com.utils.ShowUtils;
import com.utils.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import chen.easyview.R;

/**
 * Created by Chen on 2017/1/23.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //ButterKnife 需要在三个setContentView中bind
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    /**
     * 显示是否正在加载中
     */
    public void showLoading(boolean toggle) {
        if (toggle) {
            ShowUtils.showProgressDialog(this);
        } else {
            ShowUtils.dismissProgressDialog();
        }
    }

    public void showToast(String content) {
        if (TextUtils.isValidate(content)) {
            Toast.makeText(BaseApplication.getContext(), content, Toast.LENGTH_SHORT).show();
            //  Snackbar.make(getWindow().getDecorView(), content, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * 框架使用的沉浸式,这个比较好,能在布局里面少些代码
     */
    @TargetApi(19)
    protected void setImmersive() {
        setTranslucentStatus(true);
        setStatusBarTintResource(R.color.translucent);
    }

    @TargetApi(19)
    protected void setStatusBarTintResource(int color) {
        setTranslucentStatus(true);
        //为状态栏着色
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
