package god.dictdemo.database.word;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao //访问数据库的一个接口
public interface WordDao {

    //
//    @Update
//    void  updateWords(Word... words);
//    @Delete
//    void  deleteWords(Word... words);
//
//    @Query("DELETE FROM CET")
//    void deleteAllWord();

    //根据单词名称删除表中数据
    @Query("DELETE  FROM FORGET where wordname=:name")
    void deleteWords(String name);

    //根据单词名称更新表中数据
    @Query("UPDATE FORGET SET wordclass= :wordclasss WHERE wordname = :wordname")
    void updateWords(String wordname, String wordclasss);

    //查询表中某数据是否存在
    @Query("select 1 from forget where wordname =:wordname  limit 1")
    int forgetWordisExist(String wordname);

    //得到表格里面的全部数据
    @Query(" SELECT example,english,phonetic,chinese FROM FOURS")
    LiveData<List<Word>> getAllWordsLive();

    //分页加载：
    @Query(" SELECT example,english,phonetic,chinese FROM FOURS limit :start,:end")
    LiveData<List<Word>> getHarfWordsLive(int start, int end);

    //模糊查询（英语）
    @Query("select * from FOURS where english like '%' || :name || '%'")
    LiveData<List<Word>> getLikeWordsEnglishLive(String name);

    //模糊查询（汉语）
    @Query("select * from FOURS where chinese like '%' || :name || '%'")
    LiveData<List<Word>> getLikeWordsChineseLive(String name);

    @Query(" SELECT testid FROM ENGLISHTEST")
    LiveData<List<EnglishTest>> getAllTextLive();

    @Query(" SELECT english,chinese FROM PHRASE")
    LiveData<List<Phrase>> getAllPhraseLive();

    @Query(" SELECT * FROM FORGET ORDER BY rowid DESC")
    LiveData<List<Forget>> getAllForgetLive();

    //根据名称查询Forget表格
    //根据字段查询
    @Query("SELECT * FROM FORGET WHERE wordname= :name")
    Forget getForgetByName(String name);

    //这三个表格的添加数据，可以忽略，没操作
    @Insert
    void insertWords(Word... words);

    @Insert
    void insertWords(EnglishTest... englishTests);

    @Insert
    void insertWords(Phrase... phrases);

    //插入遗忘等级表格数据
    @Insert
    void insertWords(Forget... forgets);

    // ==================================对于History的操作==================================
    @Insert
    void insertWords(History... histories);

    // 删除所有的搜索历史
    @Query("delete from search_history")
    void deleteAllHistories();

    @Query("select * from search_history order by rowid desc")
    LiveData<List<History>> getAllHistories();
}
