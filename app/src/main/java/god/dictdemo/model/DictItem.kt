package god.dictdemo.model

import android.os.Parcelable
import god.dictdemo.adapter.base_adapter.BaseBean
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DictItem(
    val dictId: Int,
    /**
     * 单词
     */
    val english: String,
    /**
     * 中文释义
     */
    val chinese: String,
    /**
     * 音标
     */
    val phonetic: String,
    /**
     * 例句
     */
    val example: String
) : Parcelable,BaseBean(null)