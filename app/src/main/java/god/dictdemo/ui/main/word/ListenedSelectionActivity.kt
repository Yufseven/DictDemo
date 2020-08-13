package god.dictdemo.ui.main.word

import android.os.Bundle
import android.widget.TextView
import god.dictdemo.R
import god.dictdemo.database.word.Word
import kotlinx.android.synthetic.main.activity_listened_selection.*

class ListenedSelectionActivity : BaseWordActivity() {

    override fun layoutId() = R.layout.activity_listened_selection

    override fun titleBarId() = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(listenedSelectRead)
    }

    override fun setContentView(tempResultWord: Word) {
        listenedSelectRead.playEnglish(tempResultWord.english)
    }

    override fun setCorrectView(textView: TextView, word: Word) {
        textView.text = word.chinese
    }

    override fun setInCorrectView(textView: TextView, word: Word) {
        textView.text = word.chinese
    }

    override fun initWordEvent() {
        listenedSelectRead.setOnClickListener {
            if (tempResultWord != null) {
                listenedSelectRead.playEnglish(
                    tempResultWord!!.english ?: return@setOnClickListener
                )
            }
        }
    }
}