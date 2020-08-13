package god.dictdemo.database.word;

import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;
import god.dictdemo.rxjava.RxJavaUtils;
import god.dictdemo.rxjava.task.RxIOTask;

public class WordRepository {
    private LiveData<List<Word>> allwordLive;
    private LiveData<List<EnglishTest>> allTextLive;
    private LiveData<List<Phrase>> allPhraseLive;
    private LiveData<List<Forget>> allForgetLive;
    private WordDao wordDao;
    private WordDataBase wordDatabase;

    public WordRepository(Context context) {
        //单例
        wordDatabase = WordDataBase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allwordLive = wordDao.getAllWordsLive();
        allTextLive = wordDao.getAllTextLive();
        allPhraseLive = wordDao.getAllPhraseLive();
        allForgetLive = wordDao.getAllForgetLive();
    }

    public LiveData<List<Word>> getAllwordLive() {
        return allwordLive;
    }

    public LiveData<List<Word>> getHalfwordLive(int start, int end) {
        return wordDao.getHarfWordsLive(start, end);
    }

    public LiveData<List<Word>> getLikeWordsEnglishLive(String name) {
        return wordDao.getLikeWordsEnglishLive(name);
    }

    public LiveData<List<Word>> getLikeWordsChineseLive(String name) {
        return wordDao.getLikeWordsChineseLive(name);
    }

    public LiveData<List<EnglishTest>> getAllTextLive() {
        return allTextLive;
    }

    public LiveData<List<Phrase>> getAllPhraseLive() {
        return allPhraseLive;
    }

    public LiveData<List<Forget>> getAllForgetLive() {
        return allForgetLive;
    }

    public Forget getForgetByName(String name) {
        return wordDao.getForgetByName(name);
    }

    public int forgetWordisExist(final String wordname) {
        return wordDao.forgetWordisExist(wordname);
    }


    void insertWords(final Word... words) {
        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
            @Override
            public Void doInIOThread(Object o) {
                wordDao.insertWords(words);
                return null;
            }
        });
    }

    void insertWords(final EnglishTest... englishTests) {
        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
            @Override
            public Void doInIOThread(Object o) {
                wordDao.insertWords(englishTests);
                return null;
            }
        });
    }

    void insertWords(final Phrase... phrases) {
        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
            @Override
            public Void doInIOThread(Object o) {
                wordDao.insertWords(phrases);
                return null;
            }
        });
    }

    void insertWords(final Forget... forgets) {

        // new InsertAsyncTask(wordDao).execute(words);
//        RxBusMax.getInstance().chainProcess(new Func1() {
//            @Override
//            public Object call(Object o) {
//                wordDao.insertWords(forgets);
//                return null;
//            }
//        });
        wordDao.insertWords(forgets);   // TODO
//        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
//            @Override
//            public Void doInIOThread(Object o) {
//                wordDao.insertWords(forgets);
//                return null;
//            }
//        });
    }

    void insertWords(final History... histories) {
        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
            @Override
            public Void doInIOThread(Object o) {
                wordDao.insertWords(histories);
                return null;
            }
        });
    }

    void deleteWords(final String name) {
        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
            @Override
            public Void doInIOThread(Object o) {
                wordDao.deleteWords(name);
                return null;
            }
        });
    }

    void updateWords(final String wordname, final String wordclass) {
        // new InsertAsyncTask(wordDao).execute(words);
//        RxBusMax.getInstance().chainProcess(new Func1() {
//            @Override
//            public Object call(Object o) {
//                wordDao.updateWords(wordname,wordclass);
//                return null;
//            }
//        });
        wordDao.updateWords(wordname,wordclass);    // TODO
//        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
//            @Override
//            public Void doInIOThread(Object o) {
//
//                return null;
//            }
//        });
    }

    // =============================对于History的操作=============================
    public void deleteAllHistories() {
        RxJavaUtils.doInIOThread(new RxIOTask<Object>("") {
            @Override
            public Void doInIOThread(Object o) {
                wordDao.deleteAllHistories();
                return null;
            }
        });
    }

    public LiveData<List<History>> getAllHistories(){
        return wordDao.getAllHistories();
    }
}
