package god.dictdemo.model

import android.content.Context
import androidx.paging.DataSource
import god.dictdemo.model.DictDataSource
import god.dictdemo.model.DictItem

class DictDataSourceFactory(private val context: Context) : DataSource.Factory<Int, DictItem>() {
    override fun create(): DataSource<Int, DictItem> {
        // 创建一个DataSource对象
        return DictDataSource(context)
    }
}