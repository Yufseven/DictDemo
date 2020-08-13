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
@Entity(tableName = "fours")
public class Word extends BaseBean implements Parcelable {
    //必须要有一个这种字段
    @Ignore
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int id;
    @ColumnInfo(name = "english")
    private String english;
    @ColumnInfo(name = "chinese")
    private String chinese;
    @ColumnInfo(name = "phonetic")
    private String phonetic;
    @ColumnInfo(name = "example")
    private String example;


    public Word(String english, String chinese, String phonetic, String example) {
        this.english = english;
        this.chinese = chinese;
        this.phonetic = phonetic;
        this.example = example;

    }

    protected Word(Parcel in) {
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

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

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


    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", example='" + example + '\'' +
                '}';
    }
}
