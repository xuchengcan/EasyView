package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import chen.easyview.R;
import chen.easyview.base.BaseActivity;
import chen.easyview.base.BaseApplication;
import chen.easyview.greendao.DaoSession;
import chen.easyview.greendao.TodoBean;
import chen.easyview.greendao.TodoBeanDao;


public class NoteActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.show)
    Button mShow;

    private TodoBeanDao mTodoBeanDao;
    private Query<TodoBean> mTodoBeanQuery;

    private Boolean isFromThird = false;//来自第三方跳转

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        DaoSession daoSession = BaseApplication.getDaoSession();
        mTodoBeanDao = daoSession.getTodoBeanDao();

        mTodoBeanQuery = mTodoBeanDao.queryBuilder().orderAsc(TodoBeanDao.Properties.ID).build();
        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TodoBean> list = mTodoBeanQuery.list();
                for (TodoBean todoBean:list){
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

            TodoBean todoBean = new TodoBean(null,"weixin","title",Url,"","",new Date().toString(),false);
            mTodoBeanDao.insert(todoBean);

            Intent intent = new Intent(NoteActivity.this, EasyWebViewActivity.class);
            intent.putExtra(EasyWebViewActivity.URL, Url);
            startActivity(intent);


        }


    }


}
