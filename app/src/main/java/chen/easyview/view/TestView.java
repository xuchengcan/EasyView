package chen.easyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chen on 2017/1/21.
 */

public class TestView extends View {

    Context mContext;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式为描边
        mPaint.setStrokeWidth(5f);         //设置画笔宽度为10px
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(mWidth/2,mHeight/2);
//        Path path = new Path();
//
//        path.addRect(-200,-200,200,200, Path.Direction.CW);
//
//        path.setLastPoint(-300,300);                // <-- 重置最后一个点的位置
//
//        canvas.drawPath(path,mPaint);

        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心

        Path path = new Path();                     // 创建Path

        path.lineTo(200, 200);                      // lineTo

        path.setLastPoint(200,100);                 // setLastPoint

        path.lineTo(200,0);                         // lineTo

        canvas.drawPath(path, mPaint);
    }
}
