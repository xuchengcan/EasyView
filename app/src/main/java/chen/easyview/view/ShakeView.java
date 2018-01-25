package chen.easyview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Chen on 2017/11/15.
 */

public class ShakeView extends View {

    float progress = 0;
    int width;
    float height;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿

    public ShakeView(Context context) {
        super(context);
    }

    public ShakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = width * 0.4f;
        paint.setStyle(Paint.Style.FILL);
        int color1 = Color.parseColor("#ffffff");
        int color2 = Color.parseColor("#00000000");//0098de

        //硬件加速会引起绘制问题
        paint.setShader(new RadialGradient(height / 2, height / 2, height / 2, color1, color2, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0 + progress, 0, height / 2 + progress, height, paint);
    }
}
