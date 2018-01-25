package chen.easyview.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by chennuo on 2017/11/15.
 */

public class ShakeHelpView extends View {

    int width;
    float height;

    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿

    public ShakeHelpView(Context context) {
        super(context);
    }

    public ShakeHelpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShakeHelpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;
        height = width * 0.4f;
        int color3 = Color.parseColor("#5dd7fe");
        int color4 = Color.parseColor("#0489f8");
        paint2.setShader(new LinearGradient(300, 0, 300, height, color3, color4, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, width, height, paint2);
    }
}
