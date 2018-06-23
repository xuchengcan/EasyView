package chen.easyview.activity

import android.os.Bundle
import chen.easyview.R
import com.base.BaseActivity

class KotlinActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_kotlin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
