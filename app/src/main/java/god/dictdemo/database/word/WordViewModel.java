package god.dictdemo.database.word;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        //单例
        wordRepository = new WordRepository(application);
    }

    // =====================================================get()方法: 需要开启子线程调用=====================================================

    //得到所有
    public LiveData<List<Word>> getAllWordsLive() {
        return wordRepository.getAllwordLive();
    }

    public LiveData<List<EnglishTest>> getAllEnglishTestLive() {
        return wordRepository.getAllTextLive();
    }

    public LiveData<List<Forget>> getAllForgetLive() {
        return wordRepository.getAllForgetLive();
    }

    public LiveData<List<Phrase>> getAllPhraseLive() {
        return wordRepository.getAllPhraseLive();
    }

    //得到wordname的实体类
    public Forget getForgetByName(String name) {
        return wordRepository.getForgetByName(name);
    }

    public int forgetWordisExist(final String wordname) {
        return wordRepository.forgetWordisExist(wordname);
    }

    /**
     * 部分（分页）查询
     *
     * @param start: 开始的下标数
     * @param end:   结束的下标数
     */
    public LiveData<List<Word>> getHalfWordsLive(int start, int end) {
        return wordRepository.getHalfwordLive(start, end);
    }

    // 根据关键词进行模糊查询（针对英文）
    public LiveData<List<Word>> getLikeWordsEnglishLive(final String name) {
        return wordRepository.getLikeWordsEnglishLive(name);
    }

    // 根据关键词进行模糊查询（针对中文）
    public LiveData<List<Word>> getLikeWordsChineseLive(String name) {
        return wordRepository.getLikeWordsChineseLive(name);
    }

    public LiveData<List<History>> getAllHistories() {
        return wordRepository.getAllHistories();
    }

    // =====================================================set()方法: 可以主线程直接调用=====================================================

    //根据数据类型插入
    public void insertWords(Word... words) {
        wordRepository.insertWords(words);
    }

    public void insertWords(EnglishTest... englishTests) {
        wordRepository.insertWords(englishTests);
    }

    public void insertWords(Phrase... phrases) {
        wordRepository.insertWords(phrases);
    }

    public void insertWords(Forget... forgets) {
        wordRepository.insertWords(forgets);
    }

    public void insertWords(History... histories) {
        wordRepository.insertWords(histories);
    }

    //根据wordname删除
    public void deleteWords(String wordname) {
        wordRepository.deleteWords(wordname);
    }

    //根据wordname更新wordclass
    public void updateWords(String wordname, String wordclasss) {
        wordRepository.updateWords(wordname, wordclasss);
    }

    //添加forget表格中的数据
    public void insertForget(String wordname, String wordclass, String wordtime) {
        Forget forget = new Forget(wordname, wordclass, wordtime);
        wordRepository.insertWords(forget);
    }

    public void deleteAllHistories() {
        wordRepository.deleteAllHistories();
    }
}

