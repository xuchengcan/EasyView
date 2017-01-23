package chen.easyview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;


public class ShowActivity extends BaseActivity {


    @BindView(R.id.getTinker)
    Button getTinker;
    @BindView(R.id.cleanTinker)
    Button cleanTinker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getTinker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e("Test");
            }
        });
    }

    @OnClick(R.id.cleanTinker)
    public void onClick() {
        KLog.e("test2");
    }
}
