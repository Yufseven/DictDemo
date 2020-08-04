package god.dictdemo.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import god.dictdemo.AppConfig
import god.dictdemo.R

class AppBottomBar(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : BottomNavigationView(context, attrs, defStyleAttr) {
    private val icons = intArrayOf(
        R.drawable.ic_baseline_message_stroke, R.drawable.ic_baseline_contact_stroke,
        R.drawable.ic_baseline_explore_stroke, R.drawable.ic_baseline_account_stroke
    )
    private val ids = intArrayOf(
        R.id.main_tabs_plan,
        R.id.main_tabs_word,
        R.id.main_tabs_review,
        R.id.main_tabs_my
    )

    init {
        val bottomBar = AppConfig.getBottomBar()
        val tabs = bottomBar?.tabs
        if (bottomBar != null && tabs != null) {
            val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())
            val colors = intArrayOf(
                Color.parseColor(bottomBar.activeColor),   // 按钮按下时的颜色
                Color.parseColor(bottomBar.inActiveColor)  // 按钮常规颜色
            )
            val colorStateList = ColorStateList(states, colors)
            // 给按钮设置对应的状态和颜色
            itemIconTintList = colorStateList
            itemTextColor = colorStateList
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            selectedItemId = bottomBar.selectTab    // 设置默认选中的按钮

            // 遍历添加所有的按钮
            for (tab in tabs) {
                if (tab.enable) {
                    val item = menu.add(0, ids[tab.index], tab.index, tab.title)
                    item.setIcon(icons[tab.index])
                }
            }

            // 遍历设置按钮的大小，这里需要再遍历一次是因为调用MenuItem.add()以后系统会执行removeAllViews()（原因是需要给按钮排序）的操作
            for (tab in tabs) {
                val iconSize = dp2px(tab.size)
                val menuView = getChildAt(0) as BottomNavigationMenuView
                val itemView = menuView.getChildAt(tab.index) as BottomNavigationItemView
                itemView.setIconSize(iconSize)
            }
        }
    }

    private fun dp2px(size: Int): Int =
        (context.resources.displayMetrics.density * size + 0.5f).toInt()
}