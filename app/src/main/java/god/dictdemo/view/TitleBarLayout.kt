package god.dictdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import god.dictdemo.R

class TitleBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : RelativeLayout(context, attrs, defStyleAttr) {

    var iconBack: ImageView
    var titleText: TextView
    var icon1: ImageView
    var icon2: ImageView

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBarLayout)
        val iconBackSrc = ta.getResourceId(R.styleable.TitleBarLayout_imageSrc, R.drawable.ic_baseline_arrow_back)
        val titleRes = ta.getText(R.styleable.TitleBarLayout_titleText)
        val iconSrc1 = ta.getResourceId(R.styleable.TitleBarLayout_imageSrc, R.drawable.ic_baseline_search)
        val iconSrc2 = ta.getResourceId(R.styleable.TitleBarLayout_imageSrc, R.drawable.ic_baseline_settings)
        ta.recycle()

        val view = LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this, false)
        iconBack = view.findViewById(R.id.titleBarBack)
        iconBack.setImageResource(iconBackSrc)
        titleText = view.findViewById(R.id.titleBarText)
        titleText.text = titleRes
        icon1 = view.findViewById(R.id.titleBarIcon1)
        icon1.setImageResource(iconSrc1)
        icon2 = view.findViewById(R.id.titleBarIcon2)
        icon2.setImageResource(iconSrc2)

        addView(view)
    }
}