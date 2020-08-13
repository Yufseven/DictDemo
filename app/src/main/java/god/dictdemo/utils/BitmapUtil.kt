package god.dictdemo.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.ImageView
import god.dictdemo.view.SrcScrollFrameLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BitmapUtil {

    companion object {

        fun thumbnail(fileName: String, maxWidth: Int, maxHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true   // 打开后，加载Bitmap不会将原图都加载到内存中，它只会计算得到一个比例
            // 获取这个图片的宽和高信息到options中，此时的bitmap对象为空
            var bitmap = BitmapFactory.decodeFile(fileName, options)
            options.inJustDecodeBounds = false  // 关闭后，会将计算好的比例放到该bitmap对象中
            // 计算缩放比
            val sampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inSampleSize = sampleSize
            options.inPreferredConfig = Bitmap.Config.RGB_565
            options.inPurgeable = true
            options.inInputShareable = true
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
            bitmap = BitmapFactory.decodeFile(fileName, options)
            return bitmap as Bitmap
        }

        /**
         * 根据maxWidth、maxHeight计算最合适的inSampleSize
         */
        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round(height.toFloat() / reqHeight.toFloat())
                } else {
                    inSampleSize = Math.round(width.toFloat() / reqWidth.toFloat())
                }
            }
            return inSampleSize
        }

        fun changeBitmapContrastBrightness(
            bmp: Bitmap,
            contrast: Float, brightness: Float
        ): Bitmap? {
            val cm = ColorMatrix(
                floatArrayOf(
                    contrast,
                    0f,
                    0f,
                    0f,
                    brightness,
                    0f,
                    contrast,
                    0f,
                    0f,
                    brightness,
                    0f,
                    0f,
                    contrast,
                    0f,
                    brightness,
                    0f,
                    0f,
                    0f,
                    1f,
                    0f
                )
            )
            val ret = Bitmap.createBitmap(
                bmp.width, bmp.height,
                bmp.config
            )
            val canvas = Canvas(ret)
            val paint = Paint()
            paint.colorFilter = ColorMatrixColorFilter(cm)
            canvas.drawBitmap(bmp, 0f, 0f, paint)
            return ret
        }

        fun setBackgroundFromAssets(
            activity: Activity,
            view: View,
            fileName: String
        ) {
            GlobalScope.launch {
                var bitmap: Bitmap? = null
                withContext(Dispatchers.IO) {
                    val file = FileUtil.copy2FileDir(activity, fileName)
                    bitmap = thumbnail(
                        file.absolutePath,
                        getPhoneWidth(activity),
                        getPhoneHeight(activity)
                    )
                }
                withContext(Dispatchers.Main) {
                    if (bitmap != null) {
                        when (view) {
                            is SrcScrollFrameLayout -> {
                                view.setSrcBitmap(bitmap)
                            }
                            is ImageView -> {
                                view.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
            }
        }
    }
}