package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;

public class NoteActivity extends BaseActivity {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.textView)
    TextView mTextView;

    private WebView webView;
    private WebSettings webSettings;
    private Boolean isFromThird = false;//来自第三方跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        if (Intent.ACTION_VIEW.equals(getIntent().getAction())){
            isFromThird = true;
        }

        if (isFromThird){

            KLog.i(getIntent().getData().toString());
            String Url = getIntent().getData().toString();
            webView = new WebView(NoteActivity.this);
            webView.loadUrl(Url);



        }



    }


    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                break;
            case R.id.button2:

                break;
            case R.id.button3:

                break;
            case R.id.button4:
                break;
        }
    }


}
