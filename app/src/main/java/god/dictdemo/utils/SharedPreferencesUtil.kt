package god.dictdemo.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil {
    companion object {
        private const val KEY_SP_NAME = "sp_app_config"
        private var shp: SharedPreferences? = null
        fun getInstance(context: Context): SharedPreferences {
            if (shp == null) {
                shp = context.applicationContext.getSharedPreferences(
                    KEY_SP_NAME,
                    Context.MODE_PRIVATE
                )
            }
            return shp as SharedPreferences
        }
    }
}