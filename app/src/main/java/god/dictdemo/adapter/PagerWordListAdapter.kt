package god.dictdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import god.dictdemo.R
import god.dictdemo.database.word.Word
import god.dictdemo.view.ReadWordAnimationView

class PagerWordListAdapter(private val context: Context) :
    ListAdapter<Word, PagerPhotoViewHolder>(DiffCallback) {
    private val TAG = "PagerWordListAdapter"
    private lateinit var holder: PagerPhotoViewHolder

    object DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
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
        getItem(position).apply {
            holder.desc.text = "单词：$english\n释义：$chinese\n例句：$example"
            holder.readWord.setOnClickListener {
                holder.readWord.playEnglish(english)
            }
        }
    }

}

class PagerPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val desc = itemView.findViewById<TextView>(R.id.wordDesc)
    val readWord = itemView.findViewById<ReadWordAnimationView>(R.id.readWord)
}