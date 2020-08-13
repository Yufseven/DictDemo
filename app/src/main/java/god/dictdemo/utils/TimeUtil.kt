package god.dictdemo.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object {
        fun getTodayDate(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = Date(System.currentTimeMillis())
            return simpleDateFormat.format(date)
        }
    }
}