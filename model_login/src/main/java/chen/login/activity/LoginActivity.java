package chen.login.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.ARouterUrl;
import com.base.BaseActivity;
import com.net.ResponseHandler;
import com.net.RetrofitUtils;
import com.net.RxUtil;
import com.socks.library.KLog;

import chen.login.R;
import chen.login.api.LoginService;

@Route(path = ARouterUrl.ModelLogin_LoginActivity)
public class LoginActivity extends BaseActivity {

    EditText et_phone, et_password;
    Button btn_login;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        btn_login.setOnClickListener(v -> {
            RetrofitUtils.getInstance().getService(LoginService.class).getLogin(et_phone.getText().toString(), et_password.getText().toString())
                    .map(ResponseHandler::handleResponse)
                    .compose(RxUtil.compose(this))
                    .subscribe(bean -> {
                        ARouter.getInstance().build(ARouterUrl.Main_MainActivity).navigation();
                        finish();
                    }, e -> {
                        KLog.e(e);
                        e.printStackTrace();
                    });


        });
    }

}
