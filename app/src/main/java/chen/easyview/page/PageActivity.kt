package chen.easyview.page

import android.os.Bundle
import android.widget.Button
import chen.easyview.R
import com.base.BaseActivity

/*
* https://github.com/princekin-f/Page
*/

class PageActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_page
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pageView: PageView = findViewById(R.id.page)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            pageView.startAnimation()
        }
    }
}
