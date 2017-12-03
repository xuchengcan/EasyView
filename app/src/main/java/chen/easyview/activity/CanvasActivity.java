package chen.easyview.activity;

import android.os.Bundle;
import android.view.View;

import com.socks.library.KLog;

import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import chen.easyview.view.ClickableView;

public class CanvasActivity extends BaseActivity {

    ClickableView canvasTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i();
        setContentView(R.layout.activity_canvas);

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
