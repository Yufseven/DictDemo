package god.dictdemo.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import god.dictdemo.R

class FunctionCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : CardView(context, attrs, defStyleAttr) {
    var funSrc: ImageView
    var funTitle: TextView
    var funType: TextView

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FunctionCardView)
        val src = ta.getResourceId(R.styleable.FunctionCardView_fun_src, Color.WHITE)
        val title = ta.getString(R.styleable.FunctionCardView_fun_title)
        val type = ta.getString(R.styleable.FunctionCardView_fun_type)
        ta.recycle()

        val view = LayoutInflater.from(context).inflate(R.layout.funcation_view, this, false)
        funSrc = view.findViewById<ImageView>(R.id.functionSrc)
        funTitle = view.findViewById<TextView>(R.id.functionTitle)
        funType = view.findViewById<TextView>(R.id.functionType)

        funSrc.setImageResource(src)
        funTitle.text = title
        funType.text = type

        addView(view)
    }
}