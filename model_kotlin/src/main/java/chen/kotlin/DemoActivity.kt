package chen.kotlin

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.base.ARouterUrl
import com.base.BaseActivity

@Route(path = ARouterUrl.ModelKotlin_DemoActivity)
class DemoActivity : BaseActivity() {
    override fun getContentView(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
    }
}
