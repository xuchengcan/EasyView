package chen.easyview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import java.util.Random;

import butterknife.BindView;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;

public class BubbleActivity extends BaseActivity {


    @BindView(R.id.showBubble)
    Button mShowBubble;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);

        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.layout_sample_popup, null);
        popupWindow = BubblePopupHelper.create(this, bubbleLayout);
        final Random random = new Random();
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
                bubbleLayout.setBubbleColor(Color.CYAN);
                bubbleLayout.setCornersRadius(5);
                bubbleLayout.setStrokeWidth(2);
                bubbleLayout.setStrokeColor(Color.YELLOW);
                bubbleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("hello you click me");
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
}
