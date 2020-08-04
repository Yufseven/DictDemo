package god.dictdemo.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.regex.Pattern

var IS_DEBUG = true
fun String.V(TAG: String = "super_cqw") {
    if (IS_DEBUG) {
        Log.v(TAG, "Message: $this")
    }
}

fun String.D(TAG: String = "super_cqw") {
    if (IS_DEBUG) {
        Log.d(TAG, "Message: $this")
    }
}

fun String.I(TAG: String = "super_cqw") {
    if (IS_DEBUG) {
        Log.i(TAG, "Message: $this")
    }
}

fun String.W(TAG: String = "super_cqw") {
    if (IS_DEBUG) {
        Log.w(TAG, "Message: $this")
    }
}

fun String.E(TAG: String = "super_cqw") {
    if (IS_DEBUG) {
        Log.e(TAG, "Message: $this")
    }
}

/**
 * 获得当前行数
 */
fun line(e: Exception): Int = if (e.stackTrace.isEmpty()) -1 else e.stackTrace[0].lineNumber

/**
 * 获得当前函数名
 */
fun function(e: Exception): String = if (e.stackTrace.isEmpty()) "" else e.stackTrace[0].methodName

fun copy2FileDir(context: Context, dbName: String) {
    // getFilesDir() : /data/data/packageName/files---此方法得到的的路径
    // getCacheDir() : /data/data/packageName/cache---此方法得到的的路径
    // Log.e("TAG", "getFilesDir: " + context.getFilesDir() + " getCacheDir: " + context.getCacheDir());

    // 得到文件
    val file = File(context.applicationContext.filesDir, dbName)
    var ips: InputStream? = null
    var fos: FileOutputStream? = null
    if (!file.exists()) {
        // 得到Assets中的资源
        val assets = context.applicationContext.assets
        try {
            // 通过open方法得到Assets资源的输入流
            ips = assets.open(dbName)
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
}

/**
 * 判断输入的字符串是不是数字
 */
fun String.isNumber() = Pattern.compile("[0-9]*").matcher(this).matches()

/**
 * 判断输入的字符串是不是英文
 */
fun String.isEnglish() = Pattern.compile("[a-zA-Z]").matcher(this).matches()

/**
 * 判断输入的字符串是不是汉字
 */
fun String.isChinese() = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(this).matches()
