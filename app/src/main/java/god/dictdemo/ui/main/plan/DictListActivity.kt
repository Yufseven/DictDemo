package god.dictdemo.ui.main.plan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import god.dictdemo.R
import god.dictdemo.adapter.DictAdapter
import god.dictdemo.ui.BaseActivity
import god.dictdemo.model.DictViewModel
import kotlinx.android.synthetic.main.activity_dict_list.*

class DictListActivity : BaseActivity() {
    private val dictViewModel by viewModels<DictViewModel>()  // 获取ViewModel对象
//    private val dictViewModel: DictViewModel by activityViewModels()    // 获取共享ViewModel对象（在同一Activity下）

    override fun layoutId() = R.layout.activity_dict_list

    override fun titleBarId() = -1

    override fun initEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 另一种创建ViewModel的方式
//        dictViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(DictViewModel::class.java)

        val dictAdapter = DictAdapter()
        recyclerViewDictList.apply {
//            setHasFixedSize(true)   // 优化：因为这里的总条数不会变，防止多次requestLayout
            layoutManager = LinearLayoutManager(this@DictListActivity, RecyclerView.VERTICAL, false)
            adapter = dictAdapter
        }
        dictViewModel.pagedListLiveData.observe(this, Observer { data ->
            dictAdapter.submitList(data)
        })
        dictViewModel.watchPosition.observe(this, Observer {
            recyclerViewDictList.scrollToPosition(it - 3)
        })
    }

}