package god.dictdemo.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class FileUtil {
    companion object {
        fun copy2FileDir(context: Context, filePath: String): File {
            // getFilesDir() : /data/data/packageName/files---此方法得到的的路径
            // getCacheDir() : /data/data/packageName/cache---此方法得到的的路径
            // Log.e("TAG", "getFilesDir: " + context.getFilesDir() + " getCacheDir: " + context.getCacheDir());

            // 得到文件
            val file = File(context.applicationContext.filesDir, filePath)
            var ips: InputStream? = null
            var fos: FileOutputStream? = null
            if (!file.exists()) {
                // 得到Assets中的资源
                val assets = context.applicationContext.assets
                try {
                    // 通过open方法得到Assets资源的输入流
                    ips = assets.open(filePath)
                    // 开始进行读写操作
                    fos = FileOutputStream(file)
                    val b = ByteArray(1024)
                    var len = -1
                    while (ips.read(b).also { len = it } != -1) {
                        fos.write(b, 0, len)
                        fos.flush()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    if (ips != null) {
                        try {
                            ips.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return file
        }
    }
}