package god.dictdemo.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
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

fun String.showToast(application: Application) = Toast.makeText(application,this,Toast.LENGTH_SHORT).show()

/**
 * 获取屏幕宽度
 */
fun getPhoneWidth(activity: Activity): Int {
    val outMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

/**
 * 获取屏幕高度
 */
fun getPhoneHeight(activity: Activity): Int {
    val outMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}

/**
 * 将dp转换成px
 * @param context
 * @param dpValue
 * @return
 */
fun dp2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 将像素转换成dp
 * @param context
 * @param pxValue
 * @return
 */
fun px2dp(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun px2sp(context: Context, pxValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

fun sp2px(context: Context, spValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}