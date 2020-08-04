package god.dictdemo

import android.app.Application
import android.content.res.AssetManager
import com.google.gson.Gson
import god.dictdemo.model.bottombar.BottomBar
import god.dictdemo.utils.AppGlobals
import god.dictdemo.utils.copy2FileDir
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class AppConfig : Application() {

    companion object {
        private var sBottomBar: BottomBar? = null

        @JvmStatic
        fun getBottomBar(): BottomBar? {
            if (sBottomBar == null) {
                val content = parseFile("main_tabs_config.json")
                sBottomBar = Gson().fromJson(content, BottomBar::class.java)
            }
            return sBottomBar
        }

        /**
         * 将文件解析成String
         */
        private fun parseFile(fileName: String): String? {
            var ips: InputStream? = null
            var br: BufferedReader? = null
            val builder = StringBuilder()
            try {
                ips = AppGlobals.getApplication().assets.open(fileName)
                br = BufferedReader(InputStreamReader(ips))
                var line: String? = null
                while (br.readLine().also { line = it } != null) {
                    builder.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    ips?.close()
                    br?.close()
                } catch (e: Exception) {
                }
            }
            return builder.toString()
        }
    }

}