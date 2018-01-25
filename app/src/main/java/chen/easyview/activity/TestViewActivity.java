package chen.easyview.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import chen.easyview.R;
import chen.easyview.base.BaseApplication;
import chen.easyview.view.MyFloatView;

public class TestViewActivity extends AppCompatActivity {

    MyFloatView myFV;
    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        createView();
    }

    private void createView() {

        myFV = new MyFloatView(getApplicationContext());
        myFV.setImageResource(R.drawable.im_select);  //这里简单的用自带的Icom来做演示
        myFV.setLayoutParams(new LinearLayout.LayoutParams(50,50));
        //获取WindowManager

        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        wmParams = ((BaseApplication) getApplication()).getMywmParams();

        /**
         *以下都是WindowManager.LayoutParams的相关属性
         * 具体用途可参考SDK文档
         */

        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   //设置window type

        wmParams.format = PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明

        //设置Window flag

        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        /**
         * 下面的flags属性的效果形同“锁定”。
         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL

         | LayoutParams.FLAG_NOT_FOCUSABLE

         | LayoutParams.FLAG_NOT_TOUCHABLE;
         */

        wmParams.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角，便于调整坐标

        //以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = 100;
        wmParams.height = 100;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)){
                //显示myFloatView图像
                wm.addView(myFV, wmParams);
            }else {
                requestAlertWindowPermission();
            }
        }else {
            //显示myFloatView图像
            wm.addView(myFV, wmParams);
        }

    }

    @Override

    public void onDestroy() {

        super.onDestroy();

        //在程序退出(Activity销毁）时销毁悬浮窗口

        wm.removeView(myFV);

    }

    private static final int REQUEST_CODE = 1;
    private  void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    //显示myFloatView图像
                    wm.addView(myFV, wmParams);
                }
            }
        }
    }
}
