package god.dictdemo.utils

import android.app.Application
import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.InvocationTargetException

class AppGlobals {
    companion object {
        private lateinit var sApplication: Application

        // 通过反射调用ActivityThread中的currentApplication方法来得到全局Application对象
        fun getApplication(): Application {
            try {
                val method =
                    Class.forName("android.app.ActivityThread")
                        .getDeclaredMethod("currentApplication")
                sApplication = method.invoke(null, null) as Application
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
            return sApplication
        }
    }
}