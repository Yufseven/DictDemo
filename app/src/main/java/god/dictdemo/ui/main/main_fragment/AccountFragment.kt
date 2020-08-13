package god.dictdemo.ui.main.main_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import god.dictdemo.R
import god.dictdemo.adapter.base_adapter.BaseBean
import god.dictdemo.adapter.base_adapter.BaseRecyclerAdapter
import god.dictdemo.adapter.base_adapter.BaseViewHolder
import god.dictdemo.ui.BaseFragment
import god.dictdemo.utils.BitmapUtil
import god.dictdemo.utils.px2dp
import kotlinx.android.synthetic.main.account_cell.view.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : BaseFragment() {
    private var accountAdapter: AccountAdapter? = null
    private val data = ArrayList<AccountBean>()

    override fun titleBarId() = R.id.accountTitleBar

    override fun layoutId() = R.layout.fragment_account

    override fun initEvent() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        accountTitleBar.iconBack.visibility = View.GONE

        with(data) {
            add(AccountBean(R.drawable.ic_baseline_settings, "学习计划"))
            add(AccountBean(R.drawable.ic_baseline_settings, "词汇量", true))
            add(AccountBean(R.drawable.ic_baseline_settings, "我的收藏"))
            add(AccountBean(R.drawable.ic_baseline_settings, "我的铜板", true))
            add(AccountBean(R.drawable.ic_baseline_settings, "官方商城"))
            add(AccountBean(R.drawable.ic_baseline_settings, "帮助与反馈"))
            add(AccountBean(R.drawable.ic_baseline_settings, "给我们好评", true))
            add(AccountBean(R.drawable.ic_baseline_settings, "关于我们"))
        }

        // 把Assets文件夹中的图片设置到背景上
        /*BitmapUtil.setBackgroundFromAssets(
            requireActivity(),
            accountTitleBackground,
            "splash_bg.jpg"
        )*/

        accountAdapter = AccountAdapter(R.layout.account_cell, data)

        accountList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = accountAdapter
        }

    }

    class AccountAdapter(layoutId: Int, data: List<AccountBean>) :
        BaseRecyclerAdapter(layoutId, data) {
        override fun <T : BaseBean?> convert(holder: BaseViewHolder?, baseBean: T, position: Int) {
            if (holder == null) return
            val item = baseBean as AccountBean
            with(holder.itemView) {
                accountCellDivider.visibility = if (item.isShowLine) {
                    accountCellDivider.setPadding(0, px2dp(context, 30f), 0, 0)
                    View.VISIBLE
                } else View.GONE
                accountCellIcon.setImageResource(item.icon)
                accountCellMessage.text = item.message
            }
        }
    }

    data class AccountBean(
        val icon: Int,
        val message: String,
        val isShowLine: Boolean = false
    ) : BaseBean()
}