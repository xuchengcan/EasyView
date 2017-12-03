package chen.easyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.socks.library.KLog;

import java.lang.ref.WeakReference;

import chen.easyview.R;

/**
 * Created by Chen on 2017/1/17.
 */

public class ClickableView extends View {

    Context mContext;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private MyHandler mHandler;
    private int Page = 0;//初始片段
    private int Maxpage = 10;//片段总数
    private int ShowTime = 10;//刷新时间

    public ClickableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.im_yes);

        mHandler = new MyHandler(this);

        mHandler.sendEmptyMessageDelayed(0, 0);
    }

    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式为描边
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
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

//        canvas.save();
//        canvas.translate(mWidth/2,mHeight/2);
//
//        RectF rect = new RectF(-400,-400,400,400);   // 矩形区域
//
//        for (int i=0; i<=20; i++)
//        {
//            canvas.scale(0.9f,0.9f);
//            canvas.drawRect(rect,mPaint);
//        }
//
//        canvas.restore();


        // 将画布坐标系移动到画布中央
        canvas.translate(mWidth / 2 - bitmap.getWidth() / 2, mHeight / 2 - bitmap.getWidth() / 2);

        float r = (float) Math.sqrt(2) * bitmap.getWidth() / 2;
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getWidth() / 2, r, mPaint);

        KLog.i(Page);
        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0, 0, bitmap.getWidth() / Maxpage * Page, bitmap.getHeight());

        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(0, 0, bitmap.getWidth() / Maxpage * Page, bitmap.getHeight());

        canvas.drawBitmap(bitmap, src, dst, new Paint());

        Page++;

    }

    public void show() {
        Page = 0;
        mHandler.sendEmptyMessageDelayed(0, 0);
    }

    public void stop() {
        mHandler = null;
    }

    static class MyHandler extends Handler {
        WeakReference<ClickableView> view;

        MyHandler(ClickableView view) {
            this.view = new WeakReference<ClickableView>(view);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            ClickableView mview = view.get();
            if (mview != null) {
                mview.play();
            }
        }
    }

    private void play() {
        if (Page <= Maxpage) {
            invalidate();
            if (mHandler != null) {
                mHandler.sendEmptyMessageDelayed(0, ShowTime);
            }
        }
    }

}
