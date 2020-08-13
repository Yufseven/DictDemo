package god.dictdemo.database.word;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class, EnglishTest.class, Forget.class, Phrase.class, History.class}, version = 1, exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {
    private static WordDataBase INSTANCE;

    static synchronized WordDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDataBase.class, "word.db")
                    //     .allowMainThreadQueries()    //在主线程运行
                    .createFromAsset("word.db") //预填充数据库
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return INSTANCE;

    }

    public abstract WordDao getWordDao();

}
