package chen.easyview.activity;

import android.os.Bundle;
import android.view.View;

import com.base.BaseActivity;
import com.socks.library.KLog;

import chen.easyview.R;
import chen.easyview.view.ClickableView;

public class CanvasActivity extends BaseActivity {

    ClickableView canvasTest;

    @Override
    protected int getContentView() {
        return R.layout.activity_canvas;
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
        canvasTest = (ClickableView)findViewById(R.id.canvas);
        canvasTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i();
                canvasTest.show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        canvasTest.stop();
    }
}
