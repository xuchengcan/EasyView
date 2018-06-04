package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.ARouterUrl;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import chen.easyview.R;
import chen.easyview.bean.ActivityBean;
import chen.login.activity.LoginActivity;

@Route(path = ARouterUrl.Main_MainActivity)
public class MainActivity extends BaseActivity {

    private ListView main_list;
    private List<ActivityBean> mActivities;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initList();
        main_list = (ListView)findViewById(R.id.main_list);

        MainAdapter mainAdapter = new MainAdapter();
        main_list.setAdapter(mainAdapter);
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActivities.get(position).isModel){
                    ARouter.getInstance().build(mActivities.get(position).model_url).navigation();
                }else {
                    Intent mIntent = new Intent(MainActivity.this,mActivities.get(position).mAClass);
                    startActivity(mIntent);
                }
            }
        });
    }

    private void initList() {
        mActivities = new ArrayList<>();
        mActivities.add(new ActivityBean(ShowActivity.class));
        mActivities.add(new ActivityBean(CanvasActivity.class));
        mActivities.add(new ActivityBean(BubbleActivity.class));
        mActivities.add(new ActivityBean(NoteActivity.class));
        mActivities.add(new ActivityBean(BaiduTtsActivity.class));
        mActivities.add(new ActivityBean(EasyWebViewActivity.class));
        mActivities.add(new ActivityBean(ShakeActivity.class));
        mActivities.add(new ActivityBean(ScrollingActivity.class));
        mActivities.add(new ActivityBean(MessengerActivity.class));
        mActivities.add(new ActivityBean(BookManagerActivity.class));
        mActivities.add(new ActivityBean(ARouterUrl.ModelLogin_LoginActivity));
    }


    class MainAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mActivities.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView != null)
                return convertView;
            else
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list,null);

            TextView tv = (TextView) convertView.findViewById(R.id.il_text);
            tv.setText(mActivities.get(position).name);
            return convertView;
        }
    }
}

