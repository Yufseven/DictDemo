package god.dictdemo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import god.dictdemo.R
import kotlinx.android.synthetic.main.account_icon_layout.*
import kotlinx.android.synthetic.main.contact_icon_layout.*
import kotlinx.android.synthetic.main.explore_icon_layout.*
import kotlinx.android.synthetic.main.plan_icon_layout.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val destinationMap = mapOf(
            R.id.planFragment to planMotionLayout,
            R.id.wordFragment to wordMotionLayout,
            R.id.reviewFragment to reviewMotionLayout,
            R.id.accountFragment to accountMotionLayout
        )
        navController = findNavController(R.id.fragment_main)
        if (actionBar !== null) {
            setupActionBarWithNavController(navController, AppBarConfiguration(destinationMap.keys))
        }

        destinationMap.forEach { map ->
            map.value.setOnClickListener { navController.navigate(map.key) }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            controller.popBackStack()   // 将返回栈清空，避免出现点击back键回到上一次点击
            destinationMap.values.forEach { it.progress = 0f }
//            messageMotionLayout.progress = 0f
//            contactMotionLayout.progress = 0f
//            exploreMotionLayout.progress = 0f
//            accountMotionLayout.progress = 0f
            destinationMap[destination.id]?.transitionToEnd()
//            when (destination.id) {
//                R.id.verticalListFragment2 -> messageMotionLayout.transitionToEnd()
//                R.id.contactFragment -> contactMotionLayout.transitionToEnd()
//                R.id.exploreFragment -> exploreMotionLayout.transitionToEnd()
//                R.id.accountFragment -> accountMotionLayout.transitionToEnd()
//            }
        }
    }
}