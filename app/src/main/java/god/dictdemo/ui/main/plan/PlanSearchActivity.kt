package god.dictdemo.ui.main.plan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import god.dictdemo.R
import god.dictdemo.adapter.base_adapter.BaseBean
import god.dictdemo.adapter.base_adapter.BaseRecyclerAdapter
import god.dictdemo.adapter.base_adapter.BaseViewHolder
import god.dictdemo.model.DictItem
import god.dictdemo.ui.BaseActivity
import god.dictdemo.viewmodel.PlanViewModel
import kotlinx.android.synthetic.main.activity_plan_search.*
import kotlinx.android.synthetic.main.search_word_cell.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlanSearchActivity : BaseActivity() {
    private val planViewModel by viewModels<PlanViewModel>()
    private lateinit var planSearchAdapter: PlanSearchAdapter

    override fun titleBarId() = -1
    override fun layoutId() = R.layout.activity_plan_search

    override fun initEvent() {
        planSearchCancel.setOnClickListener { finish() }
        planSearchClear.setOnClickListener {
            planViewModel.clearData()
            planSearchAdapter.updateData(null)
            planSearchAdapter.notifyDataSetChanged()
        }
        planSearchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO 模拟请求数据库模糊数据，有数据之后将清除历史按键隐藏
                GlobalScope.launch {
                    planViewModel.searchDatabase(s.toString())
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO 一来如果没有历史记录，隐藏清除历史按键
        planSearchAdapter =
            PlanSearchAdapter(
                this,
                planViewModel
            )
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

        planViewModel.wordList.observe(this, Observer {
            planSearchAdapter.updateData(it)
            planSearchAdapter.notifyDataSetChanged()
        })
    }

    class PlanSearchAdapter(context: Context, planViewModel: PlanViewModel) :
        BaseRecyclerAdapter(R.layout.search_word_cell, planViewModel.wordList.value) {
        override fun <T : BaseBean?> convert(holder: BaseViewHolder?, baseBean: T, position: Int) {
            if (holder == null) return
            val item = baseBean as DictItem
            with(holder.itemView) {
                cellSearchWord.text = item.english
                cellSearchPhonetic.text =
                    resources.getString(R.string.phonetic_message, item.phonetic)
                cellSearchInfo.text = item.chinese

                setOnItemClickListener { v, position, bean ->
                    // TODO 跳转到浏览页面
                    context.startActivity(Intent(context, DictDetailActivity::class.java).apply {
                        putExtra(DictDetailActivity.KEY_MODE, DictDetailActivity.SINGLE_MODE)
                        putExtra("DICT_SINGLE", bean)
                    })
                }
                // TODO 同时将该单词加入到历史搜索的数据库内的第一个位置
            }
        }
    }
}