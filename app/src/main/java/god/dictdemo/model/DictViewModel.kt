package god.dictdemo.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import god.dictdemo.model.DictDataSourceFactory

class DictViewModel(application: Application) : AndroidViewModel(application) {

    val pagedListLiveData = DictDataSourceFactory(application)
        .toLiveData(2)

    val watchPosition = MutableLiveData<Int>()


    // ===================淘汰 使用Paging===================
    /*private val TAG = "DictViewModel"
    private val DATABASE_NAME = "dict.db"
    private var _application = application

    */
    /**
     * 单词列表
     * 提供给对内使用的对象
     *//*
    private val _dictList = MutableLiveData<ArrayList<DictItem>>()

    */
    /**
     * 单词列表
     * 提供给对外使用的对象
     *//*
    val dictList: LiveData<ArrayList<DictItem>> get() = _dictList

    */
    /**
     * 获得所有的单词
     *//*
    suspend fun fetchData() {
        withContext(Dispatchers.IO) {
            copy2FileDir(_application, DATABASE_NAME)
            val file = File(_application.filesDir, DATABASE_NAME)
            var cursor: Cursor? = null
            val result = ArrayList<DictItem>()
            try {
                val database =
                    SQLiteDatabase.openDatabase(
                        file.absolutePath,
                        null,
                        SQLiteDatabase.OPEN_READONLY
                    )
                cursor = database.rawQuery("select * from four", null)
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex("_id"))
                    val english = cursor.getString(cursor.getColumnIndex("english"))
                    val chinese = cursor.getString(cursor.getColumnIndex("chinese"))
                    val phonetic = cursor.getString(cursor.getColumnIndex("phonetic"))
                    val example = cursor.getString(cursor.getColumnIndex("example"))
                    result.add(DictItem(id, english, chinese, phonetic, example))
                }
                MainScope().launch {
                    _dictList.value = result
                }
            } catch (e: Exception) {
                e.printStackTrace()
                (e.message ?: "fetchData running error!!!").E(e, TAG = TAG)
            } finally {
                cursor?.close()
            }
            "${result.size}".I(Exception(), TAG = TAG)
        }
    }*/
}