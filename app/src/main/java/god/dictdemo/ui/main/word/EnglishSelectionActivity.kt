package god.dictdemo.ui.main.word

import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import god.dictdemo.R
import god.dictdemo.database.word.Word
import kotlinx.android.synthetic.main.activity_english_selection.*

class EnglishSelectionActivity : BaseWordActivity() {

    override fun layoutId() = R.layout.activity_english_selection

    override fun titleBarId() = -1

    override fun setContentView(tempResultWord: Word) {
        val content = "${tempResultWord.english}\n/${tempResultWord.phonetic}/"
        val span = SpannableStringBuilder(content)
        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(R.color.colorPrimaryDark, null)
        } else {
            resources.getColor(R.color.colorPrimaryDark)
        }
        span.setSpan(
            ForegroundColorSpan(color),
            0,
            content.indexOf("\n"),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            AbsoluteSizeSpan(60), 0,
            content.indexOf("\n"),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        chineseSelectText.text = span
    }

    override fun setCorrectView(textView: TextView, word: Word) {
        textView.text = word.chinese
    }

    override fun setInCorrectView(textView: TextView, word: Word) {
        textView.text = word.chinese
    }

    override fun initWordEvent() {

    }
}