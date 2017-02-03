package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;


public class ShowActivity extends BaseActivity {

    @BindView(R.id.showButton1)
    Button mShowButton1;
    @BindView(R.id.showButton2)
    Button mShowButton2;
    @BindView(R.id.showButton3)
    Button mShowButton3;
    @BindView(R.id.showButton4)
    Button mShowButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
    }

    @OnClick({R.id.showButton1, R.id.showButton2, R.id.showButton3, R.id.showButton4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showButton1:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "choose"));
                break;
            case R.id.showButton2:
                break;
            case R.id.showButton3:
                break;
            case R.id.showButton4:
                break;
        }
    }
}
