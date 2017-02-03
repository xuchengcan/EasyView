package chen.easyview.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.socks.library.KLog;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import chen.easyview.utils.UrlHelper;
import okhttp3.Call;

import static chen.easyview.R.id.show;

public class TinkerActivity extends BaseActivity {

    @BindView(R.id.get)
    Button mGet;
    @BindView(R.id.check)
    Button mCheck;
    @BindView(R.id.add)
    Button mAdd;
    @BindView(R.id.clean)
    Button mClean;
    @BindView(show)
    Button mShow;

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/easyview/";
    /**
     * 目标文件存储的文件名（补丁名称）
     */
    private String destFileName = "patch_signed.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker);
    }

    @OnClick({R.id.get, R.id.check, R.id.add, R.id.clean, show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get:
                OkHttpUtils.get()
                        .url(UrlHelper.LOCAL_URL+"/chen/"+destFileName)
                        .build()
                        .execute(new FileCallBack(destFileDir, destFileName) {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                KLog.e(e.getMessage());
                            }

                            @Override
                            public void onResponse(File response, int id) {
                                showToast(response.getPath() + ":" + response.getName()+ ":" + response.getTotalSpace()) ;
                                showToast("获取成功");
                                KLog.i("get");
                            }
                        });
                break;
            case R.id.check:
                File dir = new File(destFileDir);
                if (dir.exists()) {
                    File file = new File(dir, destFileName);
                    if (file.exists()) {
                        showToast("文件存在");
                    } else {
                        showToast("文件不存在");
                    }
                } else {
                    showToast("文件夹不存在");
                }
                break;
            case R.id.add:
                KLog.i("add");
                TinkerInstaller.onReceiveUpgradePatch(this.getApplication(), destFileDir + destFileName);
                break;
            case R.id.clean:
                File dir2 = new File(destFileDir);
                if (dir2.exists()) {
                    File file = new File(dir2, destFileName);
                    if (file.exists()) {
                        file.delete();
                        showToast("删除成功");
                    } else {
                        showToast("文件不存在");
                    }
                } else {
                    showToast("文件夹不存在");
                }
                break;
            case R.id.show:
                String temp = "2017.2.2-加载补丁-更新-签名chennuo";
                ((Button)view).setText(temp);
                showToast(temp);
                break;
        }
    }

}
