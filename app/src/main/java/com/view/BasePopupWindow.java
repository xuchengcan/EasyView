package com.view;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.socks.library.KLog;
import com.utils.DisplayUtils;

/**
 * Created by sunshine on 2017/9/20.
 */

public class BasePopupWindow extends PopupWindow {

    public static boolean change = false;

    public BasePopupWindow(Context context) {
        super(context);
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor) {

        KLog.i(Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 26 || Build.VERSION.SDK_INT < 24) {
            super.showAsDropDown(anchor);
            return;
        }

        //7.0 和 7.1 的bug
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];  // 获取控件在屏幕的位置
            anchor.getLocationOnScreen(location);
            if (Build.VERSION.SDK_INT == 25) {
                int tempheight = this.getHeight();
                if (true || tempheight == WindowManager.LayoutParams.MATCH_PARENT || DisplayUtils.SCREEN_HEIGHT_PIXELS <= tempheight) {//这句有疑问
                    setHeight(DisplayUtils.SCREEN_HEIGHT_PIXELS - location[1] - anchor.getHeight()); //7.1完全显示布局
                }
            }
            showAtLocation(anchor, Gravity.NO_GRAVITY, location[0], location[1] + anchor.getHeight());
        }

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

}
