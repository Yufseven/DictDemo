package god.dictdemo.ui.main.word

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import god.dictdemo.MediaPlayerManager
import god.dictdemo.R
import god.dictdemo.database.word.Word
import god.dictdemo.database.word.WordViewModel
import god.dictdemo.ui.BaseActivity
import god.dictdemo.ui.main.plan.DictDetailActivity
import god.dictdemo.utils.I
import kotlinx.android.synthetic.main.choose_bottom_bar_layout.*
import kotlinx.android.synthetic.main.choose_option_layout.*
import kotlinx.android.synthetic.main.choose_title_layout.*

abstract class BaseWordActivity : BaseActivity() {
    private val wordViewModel by viewModels<WordViewModel>()
    private val data = ArrayList<Word>()
    private val existsData = ArrayList<Word>()

    // 错误两次后或者答对之后的标志 = true
    protected var isWrongAgain = false
    protected var lastWord: Word? = null
    protected var tempResultWord: Word? = null
    private val id by lazy {
        mapOf(
            selectOptionOneTv to selectOptionOne,
            selectOptionTwoTv to selectOptionTwo,
            selectOptionThreeTv to selectOptionThree,
            selectOptionFourTv to selectOptionFour
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeData()
    }

    override fun onRestart() {
        super.onRestart()
        if (isWrongAgain) {
            lastWord = tempResultWord
            // 切换词汇并显示上一个单词
            changeData(true)
            isWrongAgain = false
        }
    }

    override fun initEvent() {
        initWordEvent()

        selectBack.setOnClickListener { finish() }
        for (entry in id) {
            entry.value.setOnClickListener {
                val intent = Intent(this, DictDetailActivity::class.java)
                intent.putExtra(DictDetailActivity.KEY_MODE, DictDetailActivity.ANSWER_MODE)
                val word = entry.key.tag as Word
                if (tempResultWord == word) {
                    "回答正确".I()
                    // TODO 整个背景变绿，叮————表示正确
                    setAnswerResultStyle(
                        entry.value,
                        true,
                        R.raw.correct,
                        getChildImageView(entry)
                    )

                    isWrongAgain = true
                    intent.putExtra("DICT_ANSWER", tempResultWord)
                    startActivity(intent)
                } else {
                    "回答错误".I()
                    // TODO 整个背景变红，哒哒————表示错误
                    setAnswerResultStyle(
                        entry.value,
                        false,
                        R.raw.error,
                        getChildImageView(entry)
                    )

                    if (isWrongAgain) {
                        intent.putExtra("DICT_ANSWER", tempResultWord)
                        startActivity(intent)
                    } else isWrongAgain = true
                }
            }
        }
        selectLastWord.setOnClickListener {
            startDictFromView(it)
        }
        // 底部按钮
        selectTip.setOnClickListener {
            startDictFromView(it)
        }
        selectRead.setOnClickListener {
            selectRead.playEnglish(tempResultWord!!.english)
        }
    }

    protected fun changeData(isNextWord: Boolean = false) {
        wordViewModel.allWordsLive.observe(this, Observer {
            if (it.isNotEmpty()) {
                data.addAll(it)
                tempResultWord = getRandomWord()

                // ui
                val randomTextView = id.keys.random()
                selectTip.tag = tempResultWord
                for (textView in id.keys) {
                    if (textView == randomTextView) {
                        setCorrectView(textView, tempResultWord!!)
                        textView.tag = tempResultWord
                    } else {
                        val tempWord = getRandomWord()
                        setInCorrectView(textView, tempWord)
                        textView.tag = tempWord
                    }
                }
                if (isNextWord && lastWord != null) {
                    selectLastWord.text = lastWord!!.chinese
                    selectLastWord.tag = lastWord
                }

                setContentView(tempResultWord!!)
            }
        })
    }

    private fun getChildImageView(entry: Map.Entry<TextView, CardView>): ImageView? {
        for (i in 0 until entry.value.childCount) {
            if (entry.value.getChildAt(i) is ImageView) {
                return entry.value.getChildAt(i) as ImageView
            }
        }
        return null
    }

    private fun setAnswerResultStyle(
        view: CardView,
        isCorrect: Boolean,
        audioId: Int,
        imageView: ImageView?
    ) {
        MediaPlayerManager().play(this, audioId)

        if (isCorrect) {
            view.foreground = ColorDrawable(Color.parseColor("#8800FF90"))
            imageView?.setImageResource(R.drawable.ic_correct)
        } else {
            view.foreground = ColorDrawable(Color.parseColor("#88FF0000"))
            imageView?.setImageResource(R.drawable.ic_incorrect)
        }
        imageView?.visibility = View.VISIBLE

        // 还原
        view.postDelayed({
            view.foreground = ColorDrawable(Color.TRANSPARENT)
            imageView?.visibility = View.GONE
        }, 1000)
    }

    protected fun startDictFromView(it: View) {
        startActivity(Intent(this, DictDetailActivity::class.java).apply {
            putExtra(DictDetailActivity.KEY_MODE, DictDetailActivity.ANSWER_MODE)
            putExtra("DICT_ANSWER", it.tag as Word)
        })
    }

    private fun getRandomWord(): Word {
        val randomBean = data.random()
        if (existsData.contains(randomBean)) {
            getRandomWord()
        } else {
            existsData.add(0, randomBean)
        }
        return randomBean
    }


    abstract fun setContentView(tempResultWord: Word)
    abstract fun initWordEvent()
    abstract fun setCorrectView(textView: TextView, word: Word)
    abstract fun setInCorrectView(textView: TextView, word: Word)
}