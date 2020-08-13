package god.dictdemo.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.content.edit
import god.dictdemo.R
import god.dictdemo.ui.BaseActivity
import god.dictdemo.ui.main.MainActivity
import god.dictdemo.utils.BitmapUtil
import god.dictdemo.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    private val KEY_SP_INDEX = "key_sp_index"

    override fun layoutId() = R.layout.activity_splash

    override fun titleBarId() = -1

    override fun initEvent() {
        splashForward.setOnClickListener { jumpMain() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shp = SharedPreferencesUtil.getInstance(this)

        val index = shp.getInt(KEY_SP_INDEX, 0)
        if (index >= Int.MAX_VALUE - 1) {
            shp.edit { putInt(KEY_SP_INDEX, 0) }
        }
        if (index % 3 == 0) {
            showSplash(shp)
        } else {
            shp.edit {
                putInt(KEY_SP_INDEX, index.plus(1))
            }
            jumpMain()
        }
//        DictDatabase.getDatabase(application).dictDao().getDict().toString().I("cqw")
    }

    private fun showSplash(shp: SharedPreferences) {
        shp.edit {
            putInt(KEY_SP_INDEX, shp.getInt(KEY_SP_INDEX, 0).plus(1))
        }
        BitmapUtil.setBackgroundFromAssets(this, splashRootView, "splash_bg.jpg")
    }

    private fun jumpMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}