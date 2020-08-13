package god.dictdemo.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import god.dictdemo.R
import god.dictdemo.adapter.PagerWordListAdapter
import god.dictdemo.database.word.Word
import god.dictdemo.ui.BaseActivity
import god.dictdemo.model.DictViewModel
import kotlinx.android.synthetic.main.activity_dict_detail.*

class DictDetailActivity : BaseActivity() {

    companion object {
        private const val TAG = "DictDetailActivity"

        const val KEY_MODE = "KEY_JUMP_MODE"
        const val LIST_MODE = 100
        const val SINGLE_MODE = 101
        const val ANSWER_MODE = 102
    }

    private val dictViewModel by viewModels<DictViewModel>()

    //    private val dictViewModel: DictViewModel by activityViewModels()
    private lateinit var pagerWordListAdapter: PagerWordListAdapter

    override fun layoutId() = R.layout.activity_dict_detail

    override fun titleBarId() = R.id.wordTitleBar

    override fun initEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordTitleBar.icon1.visibility = View.GONE
        wordTitleBar.icon2.visibility = View.GONE

        when (intent?.getIntExtra(KEY_MODE, -1)) {
            LIST_MODE -> {
                val data = intent?.getParcelableArrayListExtra<Word>("DICT_LIST") ?: return
                pagerWordListAdapter = PagerWordListAdapter(this)
                with(pagerWordListAdapter) {
                    viewpagerWordList.adapter = this
                    submitList(data)
                }
                wordTag.visibility = View.VISIBLE

                viewpagerWordList.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
//                        wordTag.text = "${position + 1} / ${data.size}"
                        wordTag.text = getString(R.string.word_page_message,position+1,data.size)
                        dictViewModel.watchPosition.value = position + 1
                    }
                })

                viewpagerWordList.setCurrentItem(
                    intent?.getIntExtra("PHOTO_POSITION", 0) ?: 0,
                    false   // 默认是true: 会有一个平滑滚动的效果，在这里显得很累赘，不需要
                )
            }
            SINGLE_MODE -> {
                val bean = intent?.getParcelableExtra<Word>("DICT_SINGLE") ?: return
                setSingleData(bean)
            }
            ANSWER_MODE -> {
                val bean = intent?.getParcelableExtra<Word>("DICT_ANSWER") ?: return
                setSingleData(bean)
            }
            else -> return
        }
    }

    private fun setSingleData(bean: Word) {
        pagerWordListAdapter = PagerWordListAdapter(this)
        with(pagerWordListAdapter) {
            viewpagerWordList.adapter = this
            submitList(listOf(bean))
        }
        wordTag.visibility = View.GONE
        viewpagerWordList.isUserInputEnabled = false
    }
}