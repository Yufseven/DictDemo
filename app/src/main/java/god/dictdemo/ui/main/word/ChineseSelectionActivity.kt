package god.dictdemo.ui.main.word

import android.widget.TextView
import god.dictdemo.R
import god.dictdemo.database.word.Word
import kotlinx.android.synthetic.main.activity_chinese_selection.*

class ChineseSelectionActivity : BaseWordActivity() {

    override fun layoutId() = R.layout.activity_chinese_selection

    override fun titleBarId() = -1

    override fun setContentView(tempResultWord: Word) {
        val content = tempResultWord.chinese.replace("#", "\n")
        chineseSelectText.text = content
    }

    override fun setCorrectView(textView: TextView, word: Word) {
        textView.text = word.english
    }

    override fun setInCorrectView(textView: TextView, word: Word) {
        textView.text = word.english
    }

    override fun initWordEvent() {

    }
}