package god.dictdemo.database.word;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import god.dictdemo.adapter.base_adapter.BaseBean;

@Fts4
@Entity(tableName = "forget")
public class Forget extends BaseBean {
    //必须要有一个这种字段
    @Ignore
    @PrimaryKey(autoGenerate = true)
    private int rowid;
    @ColumnInfo(name="wordname")
    private String wordname;
    @ColumnInfo(name="wordclass")
    private String wordclass;
    @ColumnInfo(name="wordtime")
    private String wordtime;

    public String getWordname() {
        return wordname;
    }

//    public int getRowid() {
//        return rowid;
//    }
//
//    public void setRowid(int rowid) {
//        this.rowid = rowid;
//    }

    public void setWordname(String wordname) {
        this.wordname = wordname;
    }

    public String getWordclass() {
        return wordclass;
    }

    public void setWordclass(String wordclass) {
        this.wordclass = wordclass;
    }

    public String getWordtime() {
        return wordtime;
    }

    public void setWordtime(String wordtime) {
        this.wordtime = wordtime;
    }

    public Forget(String wordname, String wordclass, String wordtime) {
        this.wordname = wordname;
        this.wordclass = wordclass;
        this.wordtime = wordtime;
    }

    @Override
    public String toString() {
        return "Forget{" +
                "rowid=" + rowid +
                ", wordname='" + wordname + '\'' +
                ", wordclass='" + wordclass + '\'' +
                ", wordtime='" + wordtime + '\'' +
                '}';
    }
}
