package god.dictdemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import god.dictdemo.R
import god.dictdemo.view.TitleBarLayout
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment : Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(layoutId(), container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rootView.findViewById<TitleBarLayout>(titleBarId())?.iconBack?.setOnClickListener {
            popBackStack()
        }

        initEvent()
    }

    protected fun popBackStack() {
        Navigation.findNavController(rootView).popBackStack()
    }

    protected fun getRootView() = rootView

    abstract fun titleBarId(): Int

    abstract fun layoutId(): Int

    abstract fun initEvent()
}