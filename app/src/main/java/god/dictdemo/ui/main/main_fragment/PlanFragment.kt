package god.dictdemo.ui.main.main_fragment

import android.content.Intent
import android.os.Bundle
import god.dictdemo.R
import god.dictdemo.ui.BaseFragment
import god.dictdemo.ui.main.plan.PlanFormActivity
import god.dictdemo.ui.main.plan.PlanSearchActivity
import kotlinx.android.synthetic.main.fragment_plan.*

class PlanFragment : BaseFragment() {

    override fun titleBarId() = -1

    override fun layoutId() = R.layout.fragment_plan

    override fun initEvent() {
        planSearch.setOnClickListener {
            startActivity(Intent(requireContext(), PlanSearchActivity::class.java))
        }
        planForm.setOnClickListener {
            startActivity(Intent(requireContext(), PlanFormActivity::class.java))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}