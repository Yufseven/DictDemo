package god.dictdemo.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import god.dictdemo.utils.I
import god.dictdemo.MediaPlayerManager
import god.dictdemo.R
import god.dictdemo.model.DictItem

class PagerWordListAdapter(private val context: Context) :
    ListAdapter<DictItem, PagerPhotoViewHolder>(DiffCallback) {
    private val TAG = "PagerWordListAdapter"
    private lateinit var holder: PagerPhotoViewHolder
    private var mediaPlayerManager: MediaPlayerManager? = null

    object DiffCallback : DiffUtil.ItemCallback<DictItem>() {
        override fun areItemsTheSame(oldItem: DictItem, newItem: DictItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DictItem, newItem: DictItem): Boolean {
            return oldItem.dictId == newItem.dictId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerPhotoViewHolder =
        PagerPhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pager_word_view, parent, false
            )
        )


    override fun onBindViewHolder(holder: PagerPhotoViewHolder, position: Int) {
        this.holder = holder
        var isPlay = false
        var isClick = false
        getItem(position).apply {
            holder.desc.text = "序号：$dictId\n单词：$english\n释义：$chinese\n例句：$example"
            holder.readWord.setOnClickListener {
                if (isClick) return@setOnClickListener
                if (isPlay) return@setOnClickListener
                isClick = true
                holder.readWord.playAnimation()
                Toast.makeText(context, "开始播放", Toast.LENGTH_SHORT).show()
                mediaPlayerManager = MediaPlayerManager()
                mediaPlayerManager!!.play("http://dict.youdao.com/dictvoice?audio=$english",
                    listener = object : MediaPlayerManager.OnPlayListener {
                        override fun onPrepare(mp: MediaPlayer) {
                            "播放器准备就绪".I(TAG)
                            isPlay = true
                            isClick = false
                        }

                        override fun onCompleted(mp: MediaPlayer) {
                            "播放结束".I(TAG)
                            isPlay = false
                            isClick = false
                            holder.readWord.cancelAnimation()
                            holder.readWord.progress = 0f
                        }

                        override fun onError(mp: MediaPlayer, what: Int, extra: Int) {
                            "播放错误：what = $what\textra = $extra".I(TAG)
                            isPlay = false
                            isClick = false
                        }
                    })
            }
        }
    }

    fun release() {
        if (holder.readWord.isAnimating) {
            holder.readWord.cancelAnimation()
        }
    }

}

class PagerPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val desc = itemView.findViewById<TextView>(R.id.wordDesc)
    val readWord = itemView.findViewById<LottieAnimationView>(R.id.readWord)
}