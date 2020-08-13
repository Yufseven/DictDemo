package god.dictdemo

import android.app.Application

class AppConfig : Application() {

    companion object {
        const val READ_WORD_PATH = "http://dict.youdao.com/dictvoice?audio="
    }

}