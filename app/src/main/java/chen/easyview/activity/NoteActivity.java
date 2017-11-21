package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import chen.easyview.base.BaseApplication;
import chen.easyview.base.BaseConfig;
import chen.easyview.base.UrlHelper;
import chen.easyview.greendao.DaoSession;
import chen.easyview.greendao.TodoBean;
import chen.easyview.greendao.TodoBeanDao;
import chen.easyview.net.JsonParser;
import okhttp3.Call;


public class NoteActivity extends BaseActivity {

    Toolbar mToolbar;
    RecyclerView mList;
    Button mShow;
    EditText serverIp;
    Button check;
    Button post;
    TextView text;

    private TodoBeanDao mTodoBeanDao;
    private Query<TodoBean> mTodoBeanQuery;
    List<TodoBean> list;

    private Boolean isFromThird = false;//来自第三方跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mToolbar = findViewById(R.id.toolbar);
        mList = findViewById(R.id.list);
        mShow = findViewById(R.id.show);
        serverIp = findViewById(R.id.server_ip);
        check = findViewById(R.id.check);
        post = findViewById(R.id.post);
        text = findViewById(R.id.text);

        serverIp.setText(BaseConfig.SERVER_IP);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseConfig.SERVER_IP = serverIp.getText().toString();
                showToast("修改成功");
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post()
                        .url(BaseConfig.SERVER_IP + UrlHelper.TODO_URL)
                        .addParams("name", "xuchengcan")
                        .addParams("json", JsonParser.serializeToJson(list))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                KLog.e();
                                showToast("请求失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                showToast(response);
                                text.setText(response);
                            }
                        });
            }
        });


        DaoSession daoSession = BaseApplication.getDaoSession();
        mTodoBeanDao = daoSession.getTodoBeanDao();
        mTodoBeanQuery = mTodoBeanDao.queryBuilder().orderAsc(TodoBeanDao.Properties.ID).build();
        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.i();
                list = mTodoBeanQuery.list();
                for (TodoBean todoBean : list) {
                    KLog.i(todoBean.toString());
                }
            }
        });


        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            isFromThird = true;
        }

        if (isFromThird) {


            KLog.i(getIntent().getData().toString());
            String Url = getIntent().getData().toString();

            TodoBean todoBean = new TodoBean(null, "weixin", "title", Url, "", "", "", new Date().toString(), false);
            mTodoBeanDao.insert(todoBean);

            Intent intent = new Intent(NoteActivity.this, EasyWebViewActivity.class);
            intent.putExtra(EasyWebViewActivity.URL, Url);
            startActivity(intent);


        }


    }


}
