package god.dictdemo.database.word;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import god.dictdemo.adapter.base_adapter.BaseBean;

@Fts4
@Entity(tableName = "englishtest")
public class EnglishTest extends BaseBean {
    @Ignore
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int id;
    @ColumnInfo(name="testid")
    private int testid;

    public EnglishTest(int testid) {
        this.testid = testid;
    }

    public int getTestid() {
        return testid;
    }

    public void setTestid(int testid) {
        this.testid = testid;
    }

    @Override
    public String toString() {
        return "EnglishTest{" +
                "id=" + id +
                ", testid=" + testid +
                '}';
    }
}
