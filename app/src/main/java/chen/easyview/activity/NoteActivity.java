package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import chen.easyview.R;
import chen.easyview.greendao.DaoMaster;
import chen.easyview.greendao.DaoSession;
import chen.easyview.greendao.TodoBean;
import chen.easyview.greendao.TodoBeanDao;
import com.base.BaseActivity;
import com.base.BaseConfig;
import com.socks.library.KLog;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;


public class NoteActivity extends BaseActivity {

    public static final boolean ENCRYPTED = false;//数据库加密flag

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
    protected int getContentView() {
        return R.layout.activity_note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//                BaseConfig.SERVER_IP = serverIp.getText().toString();
                showToast("修改成功");
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        DaoSession daoSession ;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(NoteActivity.this, ENCRYPTED ? "easyview-db-encrypted" : "easyview-db");
        //数据库密码super-secret
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

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
