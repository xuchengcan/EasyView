package chen.easyview.activity;

import android.os.Bundle;
import android.view.View;

import com.socks.library.KLog;

import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import chen.easyview.view.ClickableView;

public class CanvasActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i();
        setContentView(R.layout.activity_canvas);

        final ClickableView canvasTest = (ClickableView)findViewById(R.id.canvas);
        canvasTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i();
                canvasTest.show();
            }
        });
    }
}
