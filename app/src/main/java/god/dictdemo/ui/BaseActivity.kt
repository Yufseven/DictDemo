package god.dictdemo.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import god.dictdemo.view.TitleBarLayout

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        findViewById<TitleBarLayout>(titleBarId())?.iconBack?.setOnClickListener {
            finish()
        }
        initEvent()
    }

    protected abstract fun layoutId(): Int
    protected abstract fun titleBarId(): Int
    protected abstract fun initEvent()
}