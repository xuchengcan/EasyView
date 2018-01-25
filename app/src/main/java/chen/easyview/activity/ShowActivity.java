package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.PermissionHelp.PermissionsPageManager;
import com.socks.library.KLog;
import com.utils.DoubleClickUtils;
import com.view.DialogBox.Dialogbox_edittext;
import com.view.DialogBox.Dialogbox_permission;
import com.view.DialogBox.Dialogbox_tips;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import okhttp3.Call;


public class ShowActivity extends BaseActivity implements View.OnClickListener {

    Button mShowButton1;
    Button mShowButton2;
    Button mShowButton3;
    Button mShowButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        mShowButton1 = findViewById(R.id.showButton1);
        mShowButton2 = findViewById(R.id.showButton2);
        mShowButton3 = findViewById(R.id.showButton3);
        mShowButton4 = findViewById(R.id.showButton4);

        mShowButton1.setOnClickListener(this);
        mShowButton2.setOnClickListener(this);
        mShowButton3.setOnClickListener(this);
        mShowButton4.setOnClickListener(this);

        findViewById(R.id.text1).setOnClickListener(this);
        findViewById(R.id.text2).setOnClickListener(this);
    }

    @Override
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
                        .setContent("就是个提示")
                        .setOnCallback(new Dialogbox_tips.OnCallback() {
                            @Override
                            public void callback(Dialogbox_tips.DialogObject dialogObject) {
                                dialogObject.dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.showButton3:
                if (DoubleClickUtils.isDoubleClick()){
                    KLog.e("----1s---");
                    return;
                }
                OkHttpUtils.post()
                        .url("http://183.232.33.171/BusService.asmx/GetVersion")
                        .addHeader("User-Agent", "Html5Plus/1.0")
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
                try {
                    startActivity(PermissionsPageManager.getIntent(ShowActivity.this));
                } catch (Exception e) {
                    showToast("请到权限管理中心开启所需权限");
                }
                break;
            case R.id.text1:
                Dialogbox_permission.newInstance(ShowActivity.this)
                        .setTitle("获取权限")
                        .setContent("一个权限")
                        .setCancel("残忍拒绝")
                        .setOk("重新授权")
                        .setOnCallback(
                                new Dialogbox_permission.OnCallback() {
                                    @Override
                                    public void callback(Dialogbox_permission.DialogObject dialogObject) {
                                        dialogObject.dialog.cancel();
                                    }
                                }
                        )
                        .setCancelOnCallback(new Dialogbox_permission.OnCallback() {
                            @Override
                            public void callback(Dialogbox_permission.DialogObject dialogObject) {
                                finish();
                            }
                        }).show();
                break;
            case R.id.text2:
                Dialogbox_edittext.newInstance(ShowActivity.this)
                        .setTitle("请编辑")
                        .setHint("你有想说的话么？")
                        .setOk("对的")
                        .setCancel("再见")
                        .setOnCallback(new Dialogbox_edittext.OnCallback() {
                            @Override
                            public void callback(Dialogbox_edittext.DialogObject dialogObject) {
                                dialogObject.dialog.dismiss();
                            }
                        }).show();
                break;
            default:
        }
    }
}
