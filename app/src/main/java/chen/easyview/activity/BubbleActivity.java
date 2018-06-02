package chen.easyview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.base.BaseActivity;
import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import java.util.Random;

import chen.easyview.R;

public class BubbleActivity extends BaseActivity {

    Button mShowBubble;

    private PopupWindow popupWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_bubble;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.layout_sample_popup, null);
        popupWindow = BubblePopupHelper.create(this, bubbleLayout);
        final Random random = new Random();
        mShowBubble = findViewById(R.id.showBubble);
        mShowBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                /**
                 * location[0] is Left position
                 * location[1] is Top position
                 */
                v.getLocationInWindow(location);
                bubbleLayout.setArrowDirection(ArrowDirection.TOP);
                bubbleLayout.setArrowHeight(0);
                bubbleLayout.setArrowWidth(0);
                bubbleLayout.setArrowPosition(0);
                bubbleLayout.setCornersRadius(5);
                bubbleLayout.setStrokeWidth(2);
                bubbleLayout.setStrokeColor(Color.YELLOW);
                bubbleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("hello you click me");
                        popupWindow.dismiss();
                    }
                });
//                if (random.nextBoolean()) {
//                    bubbleLayout.setArrowDirection(ArrowDirection.TOP);
//                } else {
//                    bubbleLayout.setArrowDirection(ArrowDirection.BOTTOM);
//                }
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], v.getHeight() + location[1]);
            }
        });
    }


//    //拦截popup的点击事件，必须
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (popupWindow != null && popupWindow.isShowing()) {
//            popupWindow.dismiss();
//            return true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
