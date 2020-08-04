package god.dictdemo.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import god.dictdemo.R
import god.dictdemo.adapter.PagerWordListAdapter
import god.dictdemo.model.DictItem
import god.dictdemo.ui.BaseActivity
import god.dictdemo.viewmodel.DictViewModel
import kotlinx.android.synthetic.main.activity_dict_detail.*

class DictDetailActivity : BaseActivity() {

    companion object {
        const val KEY_MODE = "KEY_JUMP_MODE"
        const val LIST_MODE = 100
        const val SINGLE_MODE = 101
    }

    private val dictViewModel by viewModels<DictViewModel>()

    //    private val dictViewModel: DictViewModel by activityViewModels()
    private lateinit var pagerWordListAdapter: PagerWordListAdapter

    override fun layoutId() = R.layout.activity_dict_detail

    override fun titleBarId() = -1

    override fun initEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent?.getIntExtra(KEY_MODE, -1)) {
            LIST_MODE -> {
                val data = intent?.getParcelableArrayListExtra<DictItem>("DICT_LIST") ?: return
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
                        wordTag.text = "${position + 1} / ${data.size}"
                        dictViewModel.watchPosition.value = position + 1
                    }
                })

                viewpagerWordList.setCurrentItem(
                    intent?.getIntExtra("PHOTO_POSITION", 0) ?: 0,
                    false
                )
            }
            SINGLE_MODE -> {
                val bean = intent?.getParcelableExtra<DictItem>("DICT_SINGLE") ?: return
                val data = ArrayList<DictItem>()
                data.add(bean)
                pagerWordListAdapter = PagerWordListAdapter(this)
                with(pagerWordListAdapter) {
                    viewpagerWordList.adapter = this
                    submitList(data)
                }
                wordTag.visibility = View.GONE
                viewpagerWordList.isUserInputEnabled = false
            }
            else -> return
        }
    }
}