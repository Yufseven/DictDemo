package god.dictdemo.database.word;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity(tableName = "phrase")
public class Phrase {
    //必须要有一个这种字段
    @Ignore
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int id;
    @ColumnInfo(name="english")
    private String english;
    @ColumnInfo(name="chinese")
    private String chinese;

    public Phrase(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}
