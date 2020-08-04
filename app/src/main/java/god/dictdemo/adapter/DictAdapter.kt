package god.dictdemo.adapter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import god.dictdemo.R
import god.dictdemo.model.DictItem
import god.dictdemo.ui.main.plan.DictDetailActivity

//class DictAdapter : ListAdapter<DictItem, MyViewHolder>(DIFFCALLBACK) {
class DictAdapter : PagedListAdapter<DictItem, MyViewHolder>(DIFFCALLBACK) {
    object DIFFCALLBACK : DiffUtil.ItemCallback<DictItem>() {
        override fun areItemsTheSame(oldItem: DictItem, newItem: DictItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: DictItem, newItem: DictItem) =
            oldItem.dictId == newItem.dictId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val holder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.dict_cell, parent, false)
        )

        // 点击后跳转到ViewPager页面
        holder.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context, DictDetailActivity::class.java).apply {
                putExtra(DictDetailActivity.KEY_MODE, DictDetailActivity.LIST_MODE)
                putParcelableArrayListExtra("DICT_LIST", ArrayList(currentList!!))
                putExtra("PHOTO_POSITION", holder.adapterPosition)
            })
        }
        holder.itemView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 开始动画---->缩小
                    v.startAnimation(ScaleAnimation(
                        1f, 0.95f, 1f, 0.95f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                    ).apply {
                        duration = 200
                        fillAfter = true
                    })
                }
                MotionEvent.ACTION_MOVE -> {
                    // 开始动画---->放大（慢）
                }
                MotionEvent.ACTION_CANCEL -> {
                    // 结束动画
                    Handler().postDelayed({
                        v.clearAnimation()
                    }, 80)
                }
                MotionEvent.ACTION_UP -> {
                    // 结束动画
                    v.clearAnimation()
                    v.performClick()
                }
            }
            true
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dictItem = getItem(position) ?: return
        with(dictItem) {
            holder.desc.text = "序号：$dictId\n单词：$english\n释义：$chinese"
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val desc = itemView.findViewById<TextView>(R.id.cellDictTextView)
}