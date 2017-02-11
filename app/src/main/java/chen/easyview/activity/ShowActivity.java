package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.DialogBox.Dialogbox_tips;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import okhttp3.Call;


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
                Dialogbox_tips.newInstance(this)
                        .setTitle("提示")
                        .setContent("1、使用的撒反对是覅急啊卡积分；啊老看见对方\n2、但是发射点犯得上犯得上" +
                                "\n3、adfasdfadfsfafdadfasdfasdfadf" +
                                "\n4、是打发打发啊发生的地方是")
                        .setOnCallback(new Dialogbox_tips.OnCallback() {
                            @Override
                            public void callback(Dialogbox_tips.DialogObject dialogObject) {
                                dialogObject.dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.showButton3:
                OkHttpUtils.post()
                        .url("http://183.232.33.171/BusService.asmx/GetVersion")
                        .addHeader("User-Agent","Html5Plus/1.0")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                showToast(response);
                            }
                        });
                break;
            case R.id.showButton4:
                break;
        }
    }
}
