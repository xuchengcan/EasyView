package chen.easyview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;

import com.base.BaseActivity;

import chen.easyview.R;
import chen.easyview.view.ShakeView;

public class ShakeActivity extends BaseActivity {


    ShakeView shakeView;
    int width;
    ObjectAnimator animator;

    @Override
    protected int getContentView() {
        return R.layout.activity_shake;
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
        setImmersive();

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        width = dm.widthPixels;

        shakeView = findViewById(R.id.shakeView);

        animator = ObjectAnimator.ofFloat(
                shakeView, "translationX", width + width*0.37f/2);
        animator.setDuration(4500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    protected void onStart() {
        super.onStart();
        animator.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        animator.end();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animator = null;
    }
}
