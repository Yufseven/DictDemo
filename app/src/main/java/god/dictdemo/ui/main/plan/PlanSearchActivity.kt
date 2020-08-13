package god.dictdemo.ui.main.plan

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import god.dictdemo.R
import god.dictdemo.adapter.base_adapter.BaseBean
import god.dictdemo.adapter.base_adapter.BaseRecyclerAdapter
import god.dictdemo.adapter.base_adapter.BaseViewHolder
import god.dictdemo.database.word.History
import god.dictdemo.database.word.Word
import god.dictdemo.database.word.WordViewModel
import god.dictdemo.ui.BaseActivity
import god.dictdemo.utils.showToast
import kotlinx.android.synthetic.main.activity_plan_search.*
import kotlinx.android.synthetic.main.search_word_cell.view.*

class PlanSearchActivity : BaseActivity() {
    private val wordViewModel by viewModels<WordViewModel>()
    private lateinit var planSearchAdapter: PlanSearchAdapter

    override fun titleBarId() = -1
    override fun layoutId() = R.layout.activity_plan_search

    override fun initEvent() {
        planSearchCancel.setOnClickListener { finish() }
        planSearchClear.setOnClickListener {
            wordViewModel.deleteAllHistories()
            planSearchAdapter.updateData(null)
            planSearchAdapter.notifyDataSetChanged()
        }
        planSearchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 模拟请求数据库模糊数据，有数据之后将清除历史按键隐藏
                wordViewModel.getLikeWordsEnglishLive(s.toString())
                    .observe(this@PlanSearchActivity, Observer {
                        val keyword = s.toString()
                        val data = ArrayList<Word>()
                        for (word in it) {
                            if (word.english.contains(keyword) || word.chinese.contains(keyword)) {
                                data.add(word)
                            }
                            if (data.size >= 20) {
                                break
                            } else if (data.size == 0) {
                                "找不到更多数据了".showToast(application)
                                break
                            }
                        }
                        data.sortBy { word -> word.english.length }
                        if (data.isNotEmpty()) {
                            planSearchAdapter.updateData(null)
                            planSearchAdapter.notifyDataSetChanged()
                            planSearchAdapter.updateData(data)
                            planSearchAdapter.notifyDataSetChanged()
                        }
                        setClearBtnStatus(false)
                    })
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 自动弹出输入框
        with(planSearchInput){
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }

        planSearchAdapter = PlanSearchAdapter(wordViewModel)
        planSearchList.apply {
            layoutManager =
                LinearLayoutManager(this@PlanSearchActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    this@PlanSearchActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = planSearchAdapter
        }

        // 0. 先去查询有无历史记录
        wordViewModel.allHistories.observe(this, Observer {
            if (it.isEmpty()) {
                // 1. 如果没有历史记录，隐藏清除历史按键
                setClearBtnStatus(false)
            } else {
                // 2. 如果有历史记录，显示数据并显示清除历史按键
                planSearchAdapter.updateData(it)
                planSearchAdapter.notifyDataSetChanged()

                setClearBtnStatus(true)
            }
        })
    }

    private fun setClearBtnStatus(isShow: Boolean) {
        with(planSearchClear) {
            visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    class PlanSearchAdapter(val wordViewModel: WordViewModel) :
        BaseRecyclerAdapter(R.layout.search_word_cell, wordViewModel.allWordsLive.value) {
        override fun <T : BaseBean?> convert(holder: BaseViewHolder?, baseBean: T, position: Int) {
            if (holder == null) return
            when (baseBean) {
                is Word -> {
                    dealWord(holder, baseBean)
                }
                is History -> {
                    dealHistory(holder, baseBean)
                }
            }
        }

        private fun dealHistory(
            holder: BaseViewHolder,
            item: History
        ) {
            with(holder.itemView) {
                cellSearchWord.text = item.english
                cellSearchPhonetic.text =
                    resources.getString(R.string.phonetic_message, item.phonetic)
                cellSearchInfo.text = item.chinese
                setOnItemClickListener(object : OnItemClickListener {
                    override fun <T : Any?> onItemClick(v: View?, position: Int, bean: T) {
                        val data = bean as History
                        // 跳转到浏览页面
                        context.startActivity(
                            Intent(
                                context,
                                DictDetailActivity::class.java
                            ).apply {
                                putExtra(
                                    DictDetailActivity.KEY_MODE,
                                    DictDetailActivity.SINGLE_MODE
                                )
                                putExtra("DICT_SINGLE", convertBear(data))
                            })
                    }

                    private fun convertBear(item: History): Word =
                        Word(item.english, item.chinese, item.phonetic, item.example)
                })
            }
        }

        private fun dealWord(
            holder: BaseViewHolder,
            item: Word
        ) {
            with(holder.itemView) {
                cellSearchWord.text = item.english
                cellSearchPhonetic.text =
                    resources.getString(R.string.phonetic_message, item.phonetic)
                cellSearchInfo.text = item.chinese

                setOnItemClickListener(object : OnItemClickListener {
                    override fun <T : Any?> onItemClick(v: View?, position: Int, bean: T) {
                        val data = bean as Word
                        // 将该单词加入到历史搜索的数据库内的第一个位置
                        wordViewModel.insertWords(
                            History(
                                data.english,
                                data.chinese,
                                data.phonetic,
                                data.example
                            )
                        )

                        // 同时跳转到浏览页面
                        context.startActivity(
                            Intent(
                                context,
                                DictDetailActivity::class.java
                            ).apply {
                                putExtra(
                                    DictDetailActivity.KEY_MODE,
                                    DictDetailActivity.SINGLE_MODE
                                )
                                putExtra("DICT_SINGLE", data)
                            })
                    }
                })
            }
        }
    }
}