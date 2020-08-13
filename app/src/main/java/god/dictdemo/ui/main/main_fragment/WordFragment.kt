package god.dictdemo.ui.main.main_fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.core.content.edit
import androidx.recyclerview.widget.GridLayoutManager
import god.dictdemo.R
import god.dictdemo.adapter.base_adapter.BaseBean
import god.dictdemo.adapter.base_adapter.BaseRecyclerAdapter
import god.dictdemo.adapter.base_adapter.BaseViewHolder
import god.dictdemo.ui.BaseFragment
import god.dictdemo.ui.main.word.ChineseSelectionActivity
import god.dictdemo.ui.main.word.EnglishSelectionActivity
import god.dictdemo.ui.main.word.ListenedSelectionActivity
import god.dictdemo.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.fragment_word.*
import kotlinx.android.synthetic.main.word_cell.view.*

class WordFragment : BaseFragment() {
    companion object {
        private const val Chinese_Selection = "中文选词"
        private const val English_Selection = "英文选义"
        private const val Fill_Blank = "填空拼写"
        private const val Listened_Selection = "听音选义"
    }

    private val KEY_SP_TYPE = "key_sp_type"
    private var wordAdapter: BaseRecyclerAdapter? = null
    private var listPopupWindow: ListPopupWindow? = null
    private lateinit var shp: SharedPreferences
    private val types = listOf("正序", "随机")
    private val data = listOf(
        WordCell(Chinese_Selection, "阅读", R.mipmap.ic_launcher),
        WordCell(English_Selection, "阅读", R.mipmap.ic_launcher),
        WordCell(Fill_Blank, "写作", R.mipmap.ic_launcher),
        WordCell(Listened_Selection, "听力", R.mipmap.ic_launcher)
    )

    override fun titleBarId() = -1

    override fun layoutId() = R.layout.fragment_word

    override fun initEvent() {
        wordTypeCard.setOnClickListener {
            listPopupWindow?.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shp = SharedPreferencesUtil.getInstance(requireContext())

        wordType.text = getString(R.string.word_type_message, shp.getString(KEY_SP_TYPE, types[0]))

        listPopupWindow = ListPopupWindow(requireContext()).apply {
            setAdapter(
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    types
                )
            )
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            anchorView = wordType
            isModal = true
            setOnItemClickListener { parent, view, position, id ->
                shp.edit { putString(KEY_SP_TYPE, types[position]) }
                wordType.text = getString(R.string.word_type_message, types[position])
                dismiss()
            }
        }

        wordAdapter = object : BaseRecyclerAdapter(R.layout.word_cell, data) {
            override fun <T : BaseBean?> convert(
                holder: BaseViewHolder?,
                baseBean: T,
                position: Int
            ) {
                if (holder == null) return
                val item = baseBean as WordCell
                itemClickEvent(holder, item)
            }

            private fun itemClickEvent(
                holder: BaseViewHolder,
                item: WordCell
            ) {
                with(holder.itemView) {
                    functionView.funSrc.setImageResource(item.resId)
                    functionView.funTitle.text = item.title
                    functionView.funType.text = item.type

                    functionView.setOnClickListener {
                        when (item.title) {
                            Chinese_Selection -> {
                                startActivity(
                                    Intent(
                                        context,
                                        ChineseSelectionActivity::class.java
                                    )
                                )
                            }
                            English_Selection -> {
                                startActivity(
                                    Intent(
                                        context,
                                        EnglishSelectionActivity::class.java
                                    )
                                )
                            }
                            Fill_Blank -> {
                            }
                            Listened_Selection -> {
                                startActivity(
                                    Intent(
                                        context,
                                        ListenedSelectionActivity::class.java
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        with(wordList) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = wordAdapter
        }
    }


    data class WordCell(
        val title: String,
        val type: String,
        val resId: Int
    ) : BaseBean()

}