package chen.kotlin

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseActivity
import kotlinx.android.synthetic.main.activity_kotllin.*

class KotlinActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_kotllin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        tv_demo.setOnClickListener {
            val intent: Intent = Intent()
            intent.component = ComponentName(this, "chen.easyview.activity.MainActivity")//通过类名查找
            startActivity(intent)
            finish()
        }

    }

    open class Base(p: Int)

    class Derived(p: Int) : Base(p)


    class MyView : View {
        constructor(ctx: Context) : super(ctx)

        constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    }


}

open class A {
    open fun f() {
        print("A")
    }

    fun a() {
        print("a")
    }
}

interface B {
    //    fun f() { print("B") } // 接口成员默认就是“open”的
    fun b() {
        print("b")
    }
}

class C : A(), B {
//    override fun f() {
//        super<A>.f()
//    }
}