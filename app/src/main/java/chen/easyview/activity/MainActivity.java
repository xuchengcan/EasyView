package chen.easyview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import chen.easyview.R;
import chen.easyview.bean.ActivityBean;

public class MainActivity extends AppCompatActivity {

    private ListView main_list;
    private List<ActivityBean> mActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KLog.i();
        initList();
        main_list = (ListView)findViewById(R.id.main_list);

        MainAdapter mainAdapter = new MainAdapter();
        main_list.setAdapter(mainAdapter);
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(MainActivity.this,mActivities.get(position).mAClass);
                startActivity(mIntent);
            }
        });
    }

    private void initList() {
        mActivities = new ArrayList<>();
        mActivities.add(new ActivityBean(ShowActivity.class,ShowActivity.class.getSimpleName()));
        mActivities.add(new ActivityBean(CanvasActivity.class,CanvasActivity.class.getSimpleName()));
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

