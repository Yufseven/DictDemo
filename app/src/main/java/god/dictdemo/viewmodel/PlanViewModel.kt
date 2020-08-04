package god.dictdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import god.dictdemo.model.DictItem
import god.dictdemo.model.SearchWordResponse
import god.dictdemo.net.SearchWordService
import god.dictdemo.net.ServiceCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanViewModel(application: Application) : AndroidViewModel(application) {
    private val _wordList = MutableLiveData<List<DictItem>>()
    val wordList: LiveData<List<DictItem>> get() = _wordList

    fun clearData(){
        _wordList.value = null
    }

    suspend fun searchDatabase(keyword: String) {
        val data = ArrayList<DictItem>()
        withContext(Dispatchers.Main) {
            _wordList.value = data
        }
        withContext(Dispatchers.IO) {
            for (i in 0..20) {
                data.add(DictItem(i, "wocao$i", "卧槽$i", "wc$i", "第${i}声卧槽！"))
            }
        }
        withContext(Dispatchers.Main) {
            _wordList.value = data
        }
    }

    /**
     * TODO 记得搬到ViewModel中去
     */
    private suspend fun searchNetDatabase(keyword: String): SearchWordResponse? {
        var result: SearchWordResponse? = null

        val searchWordService =
            ServiceCreator.baseUrl("http://fanyi.youdao.com/").create<SearchWordService>()

        withContext(Dispatchers.IO) {
            result = searchWordService.searchWord(keyword).execute().body()
        }
        return result
    }
}