package god.dictdemo.model

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.paging.PageKeyedDataSource
import god.dictdemo.utils.E
import god.dictdemo.utils.FileUtil
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception

/**
 * 做数据获取工作的对象（直接去网络请求、从本地数据库、从网络请求再到本地数据库）
 */
class DictDataSource(private val context: Context) : PageKeyedDataSource<Int, DictItem>() {
    private val TAG = "DictViewModel"
    private val QUERY_SIZE = 30
    private val DATABASE_NAME = "dict.db"

    /**
     * 初次获取数据
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DictItem>
    ) {
        GlobalScope.launch {
            val data = fetchData(0)
            callback.onResult(data, null, 1)
        }
    }

    /**
     * 获取下一页数据的操作
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DictItem>) {
        GlobalScope.launch {
            val data = fetchData(params.key * QUERY_SIZE)
            callback.onResult(data, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DictItem>) {
        // 不用管，因为没有获取前一页这一说
    }

    /**
     * 根据size获得部分单词
     */
    suspend fun fetchData(size: Int): ArrayList<DictItem> {
        val result = ArrayList<DictItem>()
        withContext(Dispatchers.IO) {
            FileUtil.copy2FileDir(context, DATABASE_NAME)
            val file = File(context.filesDir, DATABASE_NAME)
            var cursor: Cursor? = null
            try {
                val database =
                    SQLiteDatabase.openDatabase(
                        file.absolutePath,
                        null,
                        SQLiteDatabase.OPEN_READONLY
                    )
                cursor = database.rawQuery("select * from four limit $size ,$QUERY_SIZE", null)
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex("_id"))
                    val english = cursor.getString(cursor.getColumnIndex("english"))
                    val chinese = cursor.getString(cursor.getColumnIndex("chinese"))
                    val phonetic = cursor.getString(cursor.getColumnIndex("phonetic"))
                    val example = cursor.getString(cursor.getColumnIndex("example"))
                    result.add(DictItem(id, english, chinese, phonetic, example))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                (e.message ?: "fetchData running error!!!").E(TAG)
            } finally {
                cursor?.close()
            }
        }
        return result
    }
}