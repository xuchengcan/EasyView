package chen.easyview.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import chen.easyview.utils.TextUtil;

/**
 * Created by Chen on 2017/1/23.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //ButterKnife 需要在三个setContentView中bind
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    public void showToast(String content) {

        if (TextUtil.isValidate(content)) {
            Toast.makeText(BaseApplication.getContext(), content, Toast.LENGTH_SHORT).show();
            //  Snackbar.make(getWindow().getDecorView(), content, Snackbar.LENGTH_LONG).show();
        }

    }
}
