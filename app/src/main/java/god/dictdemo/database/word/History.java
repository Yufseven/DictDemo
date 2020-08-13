package god.dictdemo.database.word;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import god.dictdemo.adapter.base_adapter.BaseBean;

@Fts4
@Entity(tableName = "search_history")
public class History extends BaseBean implements Parcelable {
    @Ignore
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    int id = 0;

    @ColumnInfo(name = "english")
    String english = null;

    @ColumnInfo(name = "chinese")
    String chinese = null;

    @ColumnInfo(name = "phonetic")
    String phonetic = null;

    @ColumnInfo(name = "example")
    String example = null;

    protected History(Parcel in) {
        id = in.readInt();
        english = in.readString();
        chinese = in.readString();
        phonetic = in.readString();
        example = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(english);
        dest.writeString(chinese);
        dest.writeString(phonetic);
        dest.writeString(example);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", example='" + example + '\'' +
                '}';
    }

    public History(String english, String chinese, String phonetic, String example) {
        this.english = english;
        this.chinese = chinese;
        this.phonetic = phonetic;
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
